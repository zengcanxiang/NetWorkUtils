package com.zengcanxiang.network.NetWorkCallback;

import com.zengcanxiang.network.NetWorkError;
import com.zengcanxiang.network.NetWorkResponse;

/**
 * Created by Administrator on 2016/3/5.
 */
public abstract class NetWorkCallback<T> {

    public abstract void onStart(int NoHttpWhat);

    public abstract void onFinish(int NoHttpWhat);

    public abstract void onSucceed(NetWorkResponse<T> response,int NoHttpWhat);

    public abstract void onError(NetWorkError netWorkError);

    public abstract void onProgress(float progress, long fileCount,int NoHttpWhat);


}
