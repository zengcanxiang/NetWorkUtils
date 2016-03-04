package com.zengcanxiang.network.OkHttpNetWork;

import com.zengcanxiang.network.NetWork;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by zengcanxiang on 2016/2/26.
 */
public class OkHttpNetWork extends NetWork {

    @Override
    public void post(HashMap<String, String> paramsMap, String url, Object callback,
                     Object tag, int... what) {
        PostFormBuilder post = OkHttpUtils.post();
        post.params(paramsMap);
        post.url(url).tag(tag).build().execute((Callback) callback);
    }

    @Override
    public void get(HashMap<String, String> paramsMap, String url, Object callback,
                    Object tag, int... what) {
        GetBuilder get = OkHttpUtils.get();
        get.params(paramsMap);
        get.url(url).tag(tag).build().execute((Callback) callback);
    }

    @Override
    public void uploadFile(String uploadUrl, HashMap<String, String> paramsMap,
                           String fileKey, File file,
                           Object callback, Object uploadFileTag, int... what) {

        OkHttpUtils.postFile()
                .url(uploadUrl)
                .file(file)
                .build()
                .execute((Callback) uploadFileTag);
    }

    @Override
    public void uploadFiles(String uploadUrl, HashMap<String, String> paramsMap,
                            ArrayList<String> fileKeys, ArrayList<String> fileNames, ArrayList<File> files,
                            Object callback, Object uploadFileTag, int... what) {
        PostFormBuilder post = OkHttpUtils.post();
        post.params(paramsMap);
        post.url(uploadUrl);
        post.tag(uploadFileTag);
        int size = fileNames.size();
        if (size != files.size() || size != fileKeys.size() || files.size() != fileKeys.size()) {
            throw new IllegalAccessError("files size not equal fileKeys size or fileNames size;\n " +
                    "files.size=" + files.size() + ",fileKeys.size=" + fileKeys.size() + ",fileNames.size=" + fileNames.size());
        }
        for (int i = 0; i < size; i++) {
            post.addFile(fileKeys.get(i), fileNames.get(i), files.get(i));
        }

        post.build().execute((Callback) callback);
    }

    @Override
    public void downLoadFile(String downUrl, String savePath, String saveFileName,
                             long connTimeOut, long readTimeOut, long writeTimeOut,
                             Object callBack, Object downFileTag, int... what) {

        final Callback callback = (Callback) callBack;
        FileCallBack fileCallBack = new FileCallBack(savePath, saveFileName) {

            @Override
            public void inProgress(float v) {
                callback.inProgress(v);
            }

            @Override
            public void onError(Call call, Exception e) {
                callback.onError(call, e);
            }

            @Override
            public void onResponse(File file) {
                callback.onResponse(file);
            }
        };

        OkHttpUtils.get().url(downUrl).tag(downFileTag).build()
                .connTimeOut(connTimeOut)
                .readTimeOut(readTimeOut)
                .writeTimeOut(writeTimeOut)
                .execute(fileCallBack);
    }

    @Override
    public void cancel(Object tag) {
        OkHttpUtils.getInstance().cancelTag(tag);
    }

    @Override
    public boolean isCallBackType(Object callback) {
        if (callback instanceof Callback) {
            return true;
        } else {
            throw new IllegalArgumentException("callback what's this? "
                    + "please extends com.zhy.http.okHttp.callback");
        }
    }

}
