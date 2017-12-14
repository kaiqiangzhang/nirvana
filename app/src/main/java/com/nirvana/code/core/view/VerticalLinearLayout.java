package com.nirvana.code.core.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by kriszhang on 16/6/3.
 */

public class VerticalLinearLayout extends ViewGroup {
    /**
     * 屏幕的高度
     */
    private int mScreenHeight;
    /**
     * 手指按下时的getScrollY
     */
    private int mScrollStart;
    /**
     * 手指抬起时的getScrollY
     */
    private int mScrollEnd;
    /**
     * 记录移动时的Y
     */
    private int mLastY;
    private int mLastDispanchY;
    /**
     * 滚动的辅助类
     */
    private Scroller mScroller;
    /**
     * 是否正在滚动
     */
    private boolean isScrolling;
    /**
     * 加速度检测
     */
    private VelocityTracker mVelocityTracker;
    /**
     * 记录当前页
     */
    private int currentPage = 0;

    private OnPageChangeListener mOnPageChangeListener;

    //added by kris 2016-06-08 14:55:43
    private int lastInterceptX;
    private int lastInterceptY;
    private int lastX;
    private int lastY;

    public VerticalLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**
         * 获得屏幕的高度
         */
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        // 初始化
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, mScreenHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            // 设置主布局的高度
            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
            lp.height = mScreenHeight * childCount;
            setLayoutParams(lp);

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child.getVisibility() != View.GONE) {
                    child.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);// 调用每个自布局的layout
                }
            }

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 如果当前正在滚动，调用父类的onTouchEventss
        Log.d("VerticalLinearLayout","onTouchEvent getY="+event.getY()+",mLastY="+mLastY);
        if (isScrolling)
            return super.onTouchEvent(event);

        int action = event.getAction();
        int y = (int) event.getY();

        obtainVelocity(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d("VL","onTouchEvent DOWN");
                mScrollStart = getScrollY();
                mLastY = y;

                break;
            case MotionEvent.ACTION_MOVE:

                Log.d("VL","onTouchEvent MOVE");
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                int dy = mLastY - y;
                // 边界值检查
                int scrollY = getScrollY();
                // 已经到达顶端，下拉多少，就往上滚动多少
                if (dy < 0 && scrollY + dy < 0) {
                    dy = -scrollY;
                }
                // 已经到达底部，上拉多少，就往下滚动多少
                if (dy > 0 && scrollY + dy > getHeight() - mScreenHeight) {
                    dy = getHeight() - mScreenHeight - scrollY;
                }

                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:

                Log.d("VL","onTouchEvent UP");
                mScrollEnd = getScrollY();

                int dScrollY = mScrollEnd - mScrollStart;

                if (wantScrollToNext())// 往上滑动
                {
                    Log.d("VL","往上滑动");
                    if (shouldScrollToNext()) {
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);

                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }

                }

                if (wantScrollToPre())// 往下滑动
                {
                    Log.d("VL","往下滑动");
                    if (shouldScrollToPre()) {
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY);

                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }
                }
                isScrolling = true;
                postInvalidate();
                recycleVelocity();
                break;
        }

        return true;
    }

    /**
     * 根据滚动距离判断是否能够滚动到下一页
     *
     * @return
     */
    private boolean shouldScrollToNext() {
        return mScrollEnd - mScrollStart > mScreenHeight / 2 || Math.abs(getVelocity()) > 600;
    }

    /**
     * 根据用户滑动，判断用户的意图是否是滚动到下一页
     *
     * @return
     */
    private boolean wantScrollToNext() {
        return mScrollEnd > mScrollStart;
    }

    /**
     * 根据滚动距离判断是否能够滚动到上一页
     *
     * @return
     */
    private boolean shouldScrollToPre() {
        return -mScrollEnd + mScrollStart > mScreenHeight / 2 || Math.abs(getVelocity()) > 600;
    }

    /**
     * 根据用户滑动，判断用户的意图是否是滚动到上一页
     *
     * @return
     */
    private boolean wantScrollToPre() {
        return mScrollEnd < mScrollStart;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        } else {

            int position = getScrollY() / mScreenHeight;

            Log.e("xxx", position + "," + currentPage);
            if (position != currentPage) {
                if (mOnPageChangeListener != null) {
                    currentPage = position;
                    mOnPageChangeListener.onPageChange(currentPage);

                }
            }

            isScrolling = false;
        }

    }

    /**
     * 获取y方向的加速度
     *
     * @return
     */
    private int getVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        return (int) mVelocityTracker.getYVelocity();
    }


    /**
     * 释放资源
     */
    private void recycleVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 初始化加速度检测器
     *
     * @param event
     */
    private void obtainVelocity(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 设置回调接口
     *
     * @param onPageChangeListener
     */
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    /**
     * 回调接口
     */
    public interface OnPageChangeListener {
        void onPageChange(int currentPage);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }


    /**
     * 外部拦截法
     *
     * 考虑一种情况，假设事件交由子元素来处理，如果父容器在ACTION_UP时返回了true，就会导致子元素无法接收到ACTION_UP事件，
     * 这个时候子元素中的onclick事件就无法触发，但是父容器比较特殊，一旦它开始拦截任何一个事件，那么后续的事件都会交给它来处理，
     * 而ACTION_UP作为最后一个事件也必定可以传递给父容器即便父容器的onInterceptTouchEvent在ACTION_UP时返回了false。
     *
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        //处理滑动冲突,也就是什么时候返回true的问题
        //规则:开始滑动时水平距离超过垂直距离的时候
        boolean intercept = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            //ACTION_DOWN这个事件，父容器必须返回false，即不拦截ACTION_DOWN事件，那么后续的ACTION_MOVE,ACTION_UP事件都会直接交由父容器处理，这个时候事件无法再传递给子元素了。
            case MotionEvent.ACTION_DOWN: //DOWN返回false,导致onTouchEvent中无法获取到DOWN
                intercept = false;
                Log.d("HV", "Intercept.DOWN");

                if (!mScroller.isFinished()) { //如果动画还没有执行完成,则打断,这种情况肯定还是由父组件处理触摸事件所以返回true
                    mScroller.abortAnimation();
                    intercept = true;
                }

                Log.d("HV", "Intercept.DOWN intercept="+intercept);
                break;
            //只有ACTION_MOVE可以根据需求来决定是否需要拦截
            case MotionEvent.ACTION_MOVE:
                Log.d("HV", "Intercept.MOVE");
                int deltaX = x - lastInterceptX;
                int deltaY = y - lastInterceptY;
//                if (Math.abs(deltaX) - Math.abs(deltaY) > 0) { //水平方向距离长  MOVE中返回true一次,后续的MOVE和UP都不会收到此请求
//                    intercept = true;
//                    Log.d("HV", "intercepted");
//                } else {
//                    intercept = false;
//                    if (currentPage==0){
//                        if (getChildAt(0) instanceof NVWebView){
//                            NVWebView nvWebView=(NVWebView) getChildAt(0);
//                            if (nvWebView.isUp && nvWebView.isBottom){
////                                intercept = true;
//                            }
//                            Log.d("VL","isUp up="+nvWebView.isUp);
//                        }
//                    }
//
//                }
                /*
                 1.向下滑动，并且isBottom则拦截
                 */
                Log.d("VL","deltaY="+deltaY);
                if(deltaY<-20 ) {//向上滑
                    Log.d("VL","向上滑了");
                    if (currentPage==0){
                        if (getChildAt(0) instanceof NVWebView){
                            NVWebView nvWebView=(NVWebView) getChildAt(0);
                            boolean isBottom;
                            if (nvWebView.getContentHeight()* nvWebView.getScale() -( nvWebView.getHeight()+nvWebView.getScrollY())==0){//webview已经滑动到底部
                                Log.d("NV","滑到低部了");
                                isBottom=true;
                            }else {
                                isBottom=false;
                            }
                            if ( isBottom){
                                //begin  return true的时候，onTouch的DOWN不执行，因此需要在这儿重新赋值
                                mScrollStart = getScrollY();
                                mLastY = y;
                                //end
                                intercept = true;
                            }
                            Log.d("VL","向上滑了，intercept"+intercept);
                        }
                    }
                }else {
                    intercept = false;
                }


                break;
            //必须返回false,因为ACTION_UP事件本身没有太多意义
            case MotionEvent.ACTION_UP:
                Log.d("HV", "Intercept.UP");
                intercept = false;

                break;
        }
        //因为DOWN返回true,所以onTouchEvent中无法获取DOWN事件,所以这里要负责设置lastX,lastY
        lastX = x;
        lastY = y;
        lastInterceptX = x; //因为先经过的DOWN,所以在MOVE的时候,这两个值已经有了
        lastInterceptY = y;
        return intercept;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}

