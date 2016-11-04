package com.zengcanxiang.network;

/**
 * 请求结果类
 */
public abstract class NetWorkResponse<T> {
    /**
     * 获取body结果字符串
     */
    public abstract String getResult();

    /**
     * 获取框架请求Response
     */
    public abstract Object getResponse();

}
