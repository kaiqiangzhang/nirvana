package com.nirvana.code.core.base;

/**
 * MVP中的P，负责逻辑处理
 * Created by kriszhang on 2017/8/7.
 */

public interface BasePresenter <T extends BaseView>{
    void attachView(T view);
    void detachView();
}
