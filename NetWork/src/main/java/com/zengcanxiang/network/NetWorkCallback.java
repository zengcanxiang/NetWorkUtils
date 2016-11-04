package com.zengcanxiang.network;

/**
 * 请求回调callback
 */
public abstract class NetWorkCallback {
    /**
     * 请求开始
     */
    public abstract void onStart();

    /**
     * 请求结束
     */
    public abstract void onFinish();

    /**
     * 请求成功
     */
    public abstract void onSucceed(NetWorkResponse response);

    /**
     * 请求失败
     */
    public abstract void onError(NetWorkError netWorkError);

    /**
     * 请求进度
     */
    public abstract void inProgress(long totalSize, float progress);

}
