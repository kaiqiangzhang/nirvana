package com.nirvana.product.mainpage;

import android.widget.ImageView;

import com.nirvana.code.core.base.BasePresenter;
import com.nirvana.code.core.base.BaseResponse;
import com.nirvana.code.core.base.BaseView;
import com.nirvana.code.model.BannerModel;
import com.nirvana.code.model.Channel;
import com.nirvana.product.mainpage.bean.HotTagBean;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 首页逻辑处理
 * Created by kriszhang on 2017/8/7.
 */

public class MainPageContract {
    public interface View extends BaseView{
        void showSnackBar();
        void upateHotTagsAdapter(BaseResponse<HotTagBean> baseReqestModel);
        void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position, BannerModel bannerModel);
    }

    public interface Presenter extends BasePresenter<View>{
        void initShareCommon();
        void initAdBanner(BGABanner zoomStackBanner);
        List<Channel> getMenuData();
        void requestHotTagsData(String url);
    }
}
