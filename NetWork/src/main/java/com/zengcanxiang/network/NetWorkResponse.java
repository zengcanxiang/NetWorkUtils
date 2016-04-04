package com.zengcanxiang.network;

/**
 * 请求结果类
 */
public abstract class NetWorkResponse<T> {

    public abstract String getString();

    public abstract T getObj(Class<T> cls);

    public abstract Object getResponse();
}
