package com.nirvana.code.core.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.nirvana.code.BuildConfig;

/**
 * Created by kriszhang on 16/6/3.
 */
public class NVWebView extends WebView implements VerticalLinearLayout.OnPageChangeListener {
    public OnWebViewScrollChangedListener mOnWebViewScrollChangedListener;
    public boolean isUp = false;

    public NVWebView(Context context) {
        super(context);
        initWebview();
    }

    public NVWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebview();

    }

    public NVWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebview();

    }

    public class NVDownloadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            getContext().startActivity(intent);
        }
    }

    /**
     * 设置默认下载
     * 下载主要有两种方式:
     * 1.自定义下载
     * 2.调用系统默认下载模块
     */
    public void setNVDownloadListener() {
        setDownloadListener(new NVDownloadListener());
    }

    /**
     * 初始化webview
     */
    public void initWebview() {
        WebSettings settings = this.getSettings();
        String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua + "; Android;AppInstalled==1");
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(false);//将图片调整到适合webview的大小
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        settings.setAllowFileAccess(true);//设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        settings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        setLongClickable(false);
        settings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (BuildConfig.DEBUG) {
                setWebContentsDebuggingEnabled(true);
            }
        }
        setNVDownloadListener();

    }


    @Override
    public void onPageChange(int currentPage) {
        isUp = false;
    }

    public void setOnWebViewScrollChangedListener(OnWebViewScrollChangedListener onWebViewScrollChangedListener) {
        this.mOnWebViewScrollChangedListener = onWebViewScrollChangedListener;
    }

    public interface OnWebViewScrollChangedListener {
        void onWebViewScrollChanged(boolean isEnd);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Check pointer index to avoid -1 (error)
        if (MotionEventCompat.findPointerIndex(event, 0) == -1) {
            return super.onTouchEvent(event);
        }

        if (event.getPointerCount() >= 2) {
            requestDisallowInterceptTouchEvent(true);
        } else {
            requestDisallowInterceptTouchEvent(false);
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        requestDisallowInterceptTouchEvent(true);


    }
}
