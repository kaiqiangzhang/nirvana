package com.nirvana.code;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.githang.statusbar.StatusBarCompat;
import com.nirvana.code.core.base.BaseActivity;
import com.nirvana.code.core.view.NVWebView;
import com.nirvana.code.core.view.RoundProgressBar;
import com.nirvana.code.share.ShareCommon;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

public class WebViewActivity extends BaseActivity implements View.OnClickListener{

    private NVWebView webView;
    private ViewGroup mRootView;
    private RoundProgressBar progressBar;
    private ShareCommon shareCommon;
    private String firstPicUrl;
    private String mUrl;
    private static final String TAG= "WebViewActivity";
    private ImageView mShareImg;
    private ImageView mBackImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.backgoud3) );
    }

    public void initView() {
        String url=getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)){
            url="http:/www.haowuyun.com/index";
        }

        progressBar=(RoundProgressBar)findViewById(R.id.roundProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        mShareImg = (ImageView) findViewById(R.id.bottom_share_btn);
        mShareImg.setOnClickListener(this);
        mBackImg =(ImageView) findViewById(R.id.bottom_back_btn);
        mBackImg.setOnClickListener(this);
        webView=(NVWebView) findViewById(R.id.webview);
        webView.initWebview();
        mRootView=(ViewGroup) findViewById(R.id.root_view);
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (url.startsWith("https://detail.m.tmall.com/item.htm")){
                    String url11 = "tmall://tmallclient/?{\"action\":”item:id=542847657165”}";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    finish();
                }else {
                    mUrl=url;
                    view.loadUrl(url);
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!TextUtils.isEmpty(mUrl) && !mUrl.equals(url)){
                    firstPicUrl="";
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressBar.getProgress()==progressBar.getMax()){
                    progressBar.setVisibility(View.GONE);
                }
//                int webviewContentHeight=webView.getContentHeight();
//                ViewGroup.LayoutParams layoutParams=webView.getLayoutParams();
//                layoutParams.height=webviewContentHeight;
//                webView.setLayoutParams(layoutParams);
//                Toast.makeText(getApplicationContext(),"webview height="+webviewContentHeight,Toast.LENGTH_SHORT).show();
            }
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              String url) {
                Log.d(TAG,"url="+url);
                if (url != null && (url.startsWith("http://")
                        || url.toString().startsWith("https://"))) {
                    if ((url.toString().startsWith("http://www.haowuyun.com/store/orig/") ||url.toString().startsWith("http://www.haowuyun.com/store/thumbs/")) && (url.toString().endsWith(".png") || url.toString().endsWith(".jpg") || url.toString().endsWith(".jpeg") || url.toString().endsWith(".JPEG"))){
                        firstPicUrl=url.toString();
                        Log.e(TAG,"firstPicUrl="+firstPicUrl);
                    }
                } else {
                    try {
                        // 不支持的跳转协议调用外部组件处理
                        Intent in = new Intent(Intent.ACTION_VIEW, Uri
                                .parse(url.toString()));
                        startActivity(in);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                return super.shouldInterceptRequest(view, url);

            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (progressBar.getProgress()==progressBar.getMax()){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (webView.canGoBack()){
                webView.goBack();
            }else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeCallbacks(null);
        mRootView.removeView(webView);
        webView.clearCache(true);
        webView.destroy();
    }

    @Override
    public int getContentViewId() {
        return R.layout.news_view;
    }

    @Override
    public int getFragmentContentViewId() {
        return 0;
    }

    @Override
    public void createPresenter() {

    }


    @Override
    public void initData() {
        shareCommon =new ShareCommon(this);
        shareCommon.initStyles(SHARE_MEDIA.QZONE.toSnsPlatform().mPlatform);
        shareCommon.initStyles(SHARE_MEDIA.SINA.toSnsPlatform().mPlatform);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.bottom_back_btn:
                finish();
                break;
            case R.id.bottom_share_btn:
                shareCommon.sharePicText(webView.getTitle(),webView.getOriginalUrl(),firstPicUrl);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoadingProgress() {

    }
}
