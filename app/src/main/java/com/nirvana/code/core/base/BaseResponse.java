package com.nirvana.code.core.base;

import java.util.List;

/**
 * Created by kriszhang on 2017/8/7.
 */

public class BaseResponse<T> {
    private int code;
    private String message;
    private List<T> data;

    public BaseResponse (){

    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
