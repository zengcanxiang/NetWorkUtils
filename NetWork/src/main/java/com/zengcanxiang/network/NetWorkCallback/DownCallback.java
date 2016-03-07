package com.zengcanxiang.network.NetWorkCallback;

import com.zengcanxiang.network.NetWorkResponse;

/**
 * Created by Administrator on 2016/3/5.
 */
public abstract class DownCallback extends NetWorkCallback{

    public abstract void onDownSucceed(String filePath);

    public abstract void onCancel(int NoHttpWhat);

    @Override
    public void onSucceed(NetWorkResponse response, int NoHttpWhat) {

    }


}
