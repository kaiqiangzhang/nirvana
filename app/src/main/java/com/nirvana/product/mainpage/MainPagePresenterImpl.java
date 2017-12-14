package com.nirvana.product.mainpage;

import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nirvana.code.Constant;
import com.nirvana.code.NivanaApplication;
import com.nirvana.code.R;
import com.nirvana.code.core.base.BaseResponse;
import com.nirvana.code.model.BannerModel;
import com.nirvana.code.model.Channel;
import com.nirvana.product.mainpage.bean.HotTagBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kriszhang on 2017/8/7.
 */

public class MainPagePresenterImpl implements MainPageContract.Presenter, BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String> {
    private BannerModel bannerModel;
    private MainPageContract.View mView;
    private List<Channel> channels;

    @Override
    public void attachView(MainPageContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void initShareCommon() {

    }

    @Override
    public void initAdBanner(BGABanner mZoomStackBanner) {
        bannerModel = new BannerModel();
        bannerModel.imgs = new ArrayList<>(4);
        bannerModel.imgs.add("http://www.haowuyun.com/store/orig/2017/0604/04184903qjg1.jpg");
        bannerModel.imgs.add("http://www.haowuyun.com/store/orig/2017/0203/03171941ye8k.jpg");
        bannerModel.imgs.add("http://www.haowuyun.com/store/orig/2017/0507/07093340uu0h.jpg");

        bannerModel.tips = new ArrayList<>(4);
        bannerModel.tips.add("如果生活不是很着急，我宁愿背起包去旅行，如若浮生，自安其心");
        bannerModel.tips.add("西湖的美，你每次去都感觉不一样？");
        bannerModel.tips.add("当孩子伸手向你要钱时，千万不能这样做，一辈子的阴影！");

        bannerModel.urls = new ArrayList<>(4);
        bannerModel.urls.add("http://www.haowuyun.com/view/376");
        bannerModel.urls.add("http://www.haowuyun.com/view/306");
        bannerModel.urls.add("http://www.haowuyun.com/view/359");

        mZoomStackBanner.setAdapter(this);
        mZoomStackBanner.setData(bannerModel.imgs, bannerModel.tips);
        mZoomStackBanner.setDelegate(this);
    }

    @Override
    public List<Channel> getMenuData() {
        channels = new ArrayList<>(8);
        Channel channel1 = new Channel();
        channel1.setId(1);
        channel1.setName("首页");
        channel1.setUrl(Constant.FIST_PAGE_URL);
        channel1.setIconResourceId(R.drawable.ic_menu_camera);
        channels.add(channel1);


        Channel channel2 = new Channel();
        channel2.setId(2);
        channel2.setName("走廊");
        channel2.setUrl(Constant.GALLERY_PAGE_URL);
        channel2.setIconResourceId(R.drawable.ic_menu_gallery);
        channels.add(channel2);

        Channel channel3 = new Channel();
        channel3.setId(3);
        channel3.setName("视频");
        channel3.setUrl(Constant.VIDEO_PAGE_URL);
        channel3.setIconResourceId(R.drawable.ic_menu_slideshow);
        channels.add(channel3);

        Channel channel4 = new Channel();
        channel4.setId(4);
        channel4.setName("问答");
        channel4.setUrl(Constant.ASK_PAGE_URL);
        channel4.setIconResourceId(R.drawable.ic_menu_send);
        channels.add(channel4);

        Channel channel5 = new Channel();
        channel5.setId(5);
        channel5.setName("亲子");
        channel5.setUrl(Constant.LOVE_PAGE_URL);
        channel5.setIconResourceId(R.drawable.ic_menu_lover);
        channels.add(channel5);

        Channel channel6 = new Channel();
        channel6.setId(6);
        channel6.setName("关于");
        channel6.setUrl(Constant.ABOUT_PAGE_URL);
        channel6.setIconResourceId(R.drawable.ic_menu_manage);
        channels.add(channel6);

        return channels;
    }

    @Override
    public void requestHotTagsData(String url) {
        RequestQueue queue = Volley.newRequestQueue(NivanaApplication.mAPP);
        StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse( String result) {
                if (TextUtils.isEmpty(result)) {
                    return;
                }
                Observable.just(result).map(new Function<String, BaseResponse<HotTagBean>>() {
                    @Override
                    public BaseResponse<HotTagBean> apply(String s) throws Exception {
                        Gson gson = new Gson();

                        List<HotTagBean> hotTagBeen = gson.fromJson(s, new TypeToken<List<HotTagBean>>() {
                        }.getType());

                        BaseResponse<HotTagBean> baseResponse = null;
                        if (hotTagBeen != null) {
                            Collections.sort(hotTagBeen);
                            baseResponse = new BaseResponse();
                            baseResponse.setCode(200);
                            baseResponse.setMessage("get data success");
                            baseResponse.setData(hotTagBeen);
                        }

                        return baseResponse;
                    }
                })
                        .observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseResponse<HotTagBean>>() {
                            @Override
                            public void accept(BaseResponse<HotTagBean> baseResponse) throws Exception {
                                mView.upateHotTagsAdapter(baseResponse);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.showError();
                            }
                        });

            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mView.showError();
            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .dontAnimate()
                .centerCrop()
                .into(itemView);

    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {

    }
}
