package com.zengcanxiang.network;

/**
 * Created by Administrator on 2016/3/5.
 */
public abstract class NetWorkResponse<T> {

    public abstract String getString();

    public abstract T getObj(Class<T> cls);

    public abstract Object getResponse();
}
