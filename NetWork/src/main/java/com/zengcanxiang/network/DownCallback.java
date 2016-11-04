package com.zengcanxiang.network;


/**
 * 下载callback
 */
public abstract class DownCallback extends NetWorkCallback {
    /**
     * 下载退出
     */
    public abstract void onCancel();

    @Override
    public void onSucceed(NetWorkResponse response) {

    }

}