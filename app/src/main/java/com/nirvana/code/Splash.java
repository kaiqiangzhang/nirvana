package com.nirvana.code;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nirvana.code.callbacks.AppBarStateChangeListener;
import com.nirvana.code.core.base.BaseActivity;
import com.nirvana.code.core.base.BaseRecyclerViewAdapter;
import com.nirvana.code.core.base.BaseResponse;
import com.nirvana.code.core.common.manager.SpaceItemDecoration;
import com.nirvana.code.core.utils.config.BasicConfig;
import com.nirvana.code.core.view.NVWebView;
import com.nirvana.code.core.view.RoundProgressBar;
import com.nirvana.code.model.BannerModel;
import com.nirvana.code.model.Channel;
import com.nirvana.code.model.MenuPopBean;
import com.nirvana.code.share.ShareCommon;
import com.nirvana.code.task.NVTaskExecutor;
import com.nirvana.code.widgets.PopMenuView;
import com.nirvana.product.mainpage.MainPageContract;
import com.nirvana.product.mainpage.MainPagePresenterImpl;
import com.nirvana.product.mainpage.adapter.MainPageHotTagsAdapter;
import com.nirvana.product.mainpage.bean.HotTagBean;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class Splash extends BaseActivity<MainPagePresenterImpl>
        implements MainPageContract.View,NavigationView.OnNavigationItemSelectedListener{

    private NVWebView webView;
    private ViewGroup mRootView;
    private RoundProgressBar progressBar;
    private String mShareTitle;
    private String mUrl;
    private GridView mGridView;
    private ShareCommon shareCommon;

    private String mSearchText;
    private Toolbar toolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout mAppBarLayout;
    private TextView mTitle;
    private BGABanner mZoomStackBanner;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EditText editText;
    private ImageView mClearImg;
    private List<Channel> channels;
    private List<HotTagBean> mHotTags;
    private RecyclerView mHotTagsGridView;
    private MainPageHotTagsAdapter mHotTagAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 123){
           if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               Log.d("zkq","has permission");
           }else {
               Log.d("zkq","no permission");
           }
           return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void initMenuView() {
        channels = mPresenter.getMenuData();
        mGridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return channels.size();
            }

            @Override
            public Object getItem(int position) {
                return channels.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(Splash.this).inflate(R.layout.channel_item, null);
                    Holder holder = new Holder();
                    holder.icon = (ImageView) convertView.findViewById(R.id.channel_icon);
                    holder.title = (TextView) convertView.findViewById(R.id.channel_title);
                    convertView.setTag(holder);

                }
                Holder holder = (Holder) convertView.getTag();
                Channel channel = (Channel) getItem(position);
                holder.icon.setImageDrawable(getResources().getDrawable(channel.getIconResourceId()));
                holder.title.setText(channel.getName());
                return convertView;
            }

            class Holder {
                TextView title;
                ImageView icon;
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Channel channel = channels.get(position);
                String channelName = channel.getName();
                String url = channel.getUrl();
                gotoWebViewActivity(url);
//                webView.loadUrl(url);
//                toolbar.setTitle(channelName);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    private String firstPicUrl = "";

    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        mGridView = (GridView) view.findViewById(R.id.common_channel);
        mHotTagsGridView = (RecyclerView) findViewById(R.id.hot_tags_grid);
        mHotTagsGridView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        mHotTagAdapter = new MainPageHotTagsAdapter(this,mHotTags,R.layout.hot_tag_item);
        mHotTagAdapter.setmOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                String url="http://www.haowuyun.com/tag/"+mHotTagAdapter.getItem(position).getName();
                gotoWebViewActivity(url);
            }
        });
        mHotTagsGridView.setAdapter(mHotTagAdapter);
        mHotTagsGridView.addItemDecoration(new SpaceItemDecoration(8));
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        mCollapsingToolbarLayout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                Log.d("zkq","onDrag"+event.getY());
                return false;
            }
        });
        mCollapsingToolbarLayout.setTitleEnabled(true);
        mAppBarLayout = (AppBarLayout)findViewById(R.id.app_barLayout);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if( state == State.EXPANDED ) {

                    //展开状态

                }else if(state == State.COLLAPSED){

                    //折叠状态

                }else {

                    //中间状态

                }
            }
        });
        mTitle = (TextView) findViewById(R.id.tv_title);
        mZoomStackBanner = (BGABanner) findViewById(R.id.banner_main_zoom_stack);
        editText = (EditText) findViewById(R.id.search_edit_text);
        mClearImg = (ImageView)findViewById(R.id.clear_search_img);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count>0) {
                    mClearImg.setVisibility(View.VISIBLE);

                }else{
                    mClearImg.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s !=null) {
                    mSearchText = s.toString();
                }

            }
        });

        mClearImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                mSearchText = "";
                editText.clearFocus();
            }
        });

        webView = (NVWebView) findViewById(R.id.webview);
        mRootView = (ViewGroup) findViewById(R.id.root_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        webView.initWebview();
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (progressBar.getProgress() == progressBar.getMax()) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {

                String webViewTitle = "分享自LoveInLog";
                super.onReceivedTitle(view, title);
                if (title != null && !TextUtils.isEmpty(title.trim())) {
                    webViewTitle = title;
                }
                mShareTitle = webViewTitle;
            }
        });
        webView.loadUrl(Constant.FIST_PAGE_URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
                mUrl = url;
                //页面内跳转打开新的Activity
                if (Constant.HOTEST_PAGE_URL.equals(url) || Constant.NEWEST_PAGE_URL.equals(url)){
                    view.loadUrl(url);
                }else {
                    gotoWebViewActivity(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!TextUtils.isEmpty(mUrl) && !mUrl.equals(url)) {
                    firstPicUrl = "";
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressBar.getProgress() == progressBar.getMax()) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              String url) {
                // TODO Auto-generated method stub
                Log.e("Splash", "url=" + url.toString());
                if (url != null && (url.toString().startsWith("http://")
                        || url.toString().startsWith("https://"))) {
                    if ((url.toString().startsWith("http://www.haowuyun.com/store/orig/") || url.toString().startsWith("http://www.haowuyun.com/store/thumbs/")) && (url.toString().endsWith(".png") || url.toString().endsWith(".jpg") || url.toString().endsWith(".jpeg") || url.toString().endsWith(".JPEG"))) {
                        firstPicUrl = url.toString();
                        Log.e("Splash", "firstPicUrl=" + firstPicUrl);
                    }
                    return super.shouldInterceptRequest(view, url);
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
            }


        });

        progressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NVTaskExecutor.scheduleTaskOnUiThread(1000, new Runnable() {
                    @Override
                    public void run() {
//                        webView.reload();
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                });
            }
        });
    }

    @Override
    public void initData() {
        mHotTags = new ArrayList<>();
        mPresenter.initAdBanner(mZoomStackBanner);
        NVTaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                initMenuView();
                shareCommon = new ShareCommon(Splash.this);
                shareCommon.initPlatforms();
                shareCommon.initStyles(SHARE_MEDIA.QZONE.toSnsPlatform().mPlatform);
            }
        });
        mPresenter.requestHotTagsData("http://www.haowuyun.com/api/hot_tags.json?maxResults=6");
    }

    public void doSearch(final String searchText, final String historUrl) {
        gotoWebViewActivity(BasicConfig.searchUrl + searchText);
    }

    /**
     * 根据url打开WebViewActivity
     * @param url
     */
    public void gotoWebViewActivity(final String url){
        Intent intent = new Intent(Splash.this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            shareCommon.sharePicText(webView.getTitle(), webView.getOriginalUrl(), firstPicUrl);
            return true;
        } else if (id == R.id.action_refresh) {
            webView.reload();
            return true;
        } else if (id == R.id.action_login) {
            gotoWebViewActivity("http://www.haowuyun.com/login");
            return true;
        } else if (id == R.id.action_sign) {
            gotoWebViewActivity("http://www.haowuyun.com/reg");
            return true;
        } else if (id == R.id.action_close) {
            webView.loadUrl("http://www.haowuyun.com/");
            return true;
        } else if (id == R.id.nav_share_pic) {
            shareCommon.shareBigPic(firstPicUrl);
            return true;
        } else if (id == android.R.id.home) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.START);
            return true;

        }else if (id == R.id.action_search){
            if (!TextUtils.isEmpty(mSearchText)) {
                doSearch(mSearchText, mUrl);
            } else {
                Snackbar.make(mCollapsingToolbarLayout, getResources().getString(R.string.search_text_empty_tips), Constant.SHOW_TIPS_DURATION)
                        .show();
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String url = "";
        String channelName = "";

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_android:
                channelName = "Android";
                url = "http://www.haowuyun.com/tag/Android/";
                break;
            case R.id.nav_java:
                channelName = "java";
                url = "http://www.haowuyun.com/tag/java/";
                break;
            case R.id.nav_js:
                channelName = "JavaScript";
                url = "http://www.haowuyun.com/tag/JavaScript/";
                break;
            case R.id.nav_h5:
                channelName = "Html5";
                url = "http://www.haowuyun.com/tag/Html5/";
                break;

        }

        gotoWebViewActivity(url);
//        webView.loadUrl(url);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
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
        return R.layout.activity_splash;
    }

    @Override
    public int getFragmentContentViewId() {
        return 0;
    }

    @Override
    public void createPresenter() {
        mPresenter = new MainPagePresenterImpl();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (shareCommon != null) {
            shareCommon.closeShare();
        }
    }


    public void initMenuPop() {
        int[] icons = {R.mipmap.icowebview_refresh, R.drawable.btn_close_v5};
        String[] texts = {"编辑", "删除"};
        List<MenuPopBean> list = new ArrayList<>();
        MenuPopBean bean = null;
        for (int i = 0; i < icons.length; i++) {
            bean = new MenuPopBean();
            bean.setIcon(icons[i]);
            bean.setText(texts[i]);
            list.add(bean);
        }
        PopMenuView pw = new PopMenuView(Splash.this, list);
        pw.setOnItemClick(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        pw.showPopupWindow(findViewById(R.id.drawer_layout));//点击右上角的那个button

    }


    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position ,BannerModel bannerModel) {
        String url=bannerModel.urls.get(position);
        if (!TextUtils.isEmpty(url)){
            gotoWebViewActivity(url);
        }

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void showSnackBar() {

    }

    @Override
    public void upateHotTagsAdapter(BaseResponse<HotTagBean> baseResponse) {
        mHotTagAdapter.refreshData(baseResponse.getData());
    }
}
