package com.nirvana.code.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.nirvana.code.R;

/**
 * 自定义View之继承自View
 * 这种方法主要用于实现一些不规则的效果
 * 集成View或ViewGroup需要自己处理warp_content(在onMeasure中处理)和padding
 * Created by kriszhang on 16/5/28.
 */
public class CircleView extends View{

    private Paint mPaint;
    private int mColor;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor=array.getColor(R.styleable.CircleView_circle_color,Color.RED);
        array.recycle();
        init();
    }

    public void init(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int padingTop=getPaddingTop();
        int paddingLeft=getPaddingLeft();
        int paddingBottom=getPaddingBottom();
        int paddingRight=getPaddingRight();
        int width=getWidth()-paddingLeft-paddingRight;
        int height=getHeight()-padingTop-paddingBottom;//为了让padding生效
        int radius=Math.min(width,height)/2;
        canvas.drawCircle(paddingLeft+width/2,padingTop+height/2,radius,mPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize=MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode==MeasureSpec.AT_MOST && heightSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(200,200);//如果是warp_content,则默认设置为200px
        }else if (widthSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(200,heightSpecSize);
        }else if (heightSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,200);
        }
    }
}
