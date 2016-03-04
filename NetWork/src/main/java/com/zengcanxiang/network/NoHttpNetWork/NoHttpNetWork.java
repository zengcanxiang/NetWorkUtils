package com.zengcanxiang.network.NoHttpNetWork;

import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;
import com.zengcanxiang.network.NetWork;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2016/3/2.
 */
public class NoHttpNetWork extends NetWork {

    private RequestQueue requestQueue;

    private DownloadQueue downloadQueue;

    /**
     * @param requestSize The core of concurrent requests
     */
    private NoHttpNetWork(int requestSize) {
        requestQueue = NoHttp.newRequestQueue(requestSize);
    }

    private void initDownloadQueue() {
        if (downloadQueue == null)
            downloadQueue = NoHttp.newDownloadQueue();
    }

    private static NoHttpNetWork mThis;

    public synchronized static NoHttpNetWork getInstance() {
        return getInstance(3);
    }

    public synchronized static NoHttpNetWork getInstance(int requestSize) {
        if (mThis == null) {
            synchronized (NoHttpNetWork.class) {
                if (mThis == null) {
                    mThis = new NoHttpNetWork(requestSize);
                }
            }
        }
        return mThis;
    }

    /**
     * @param what 用来标志请求的what, 类似handler的what一样，这里用来区分请求
     */
    @Override
    public void post(HashMap<String, String> paramsMap, String url,
                     Object callback, Object tag, int... what) {
        netWork(paramsMap, url, RequestMethod.POST, callback, tag, what);
    }

    @Override
    public void get(HashMap<String, String> paramsMap, String url,
                    Object callback, Object tag, int... what) {
        netWork(paramsMap, url, RequestMethod.GET, callback, tag, what);
    }

    @Override
    public void uploadFile(String uploadUrl, HashMap<String, String> paramsMap,
                           String fileKey, File file,
                           Object callback, Object uploadFileTag, int... what) {
        ArrayList<String> fileKeys = new ArrayList<>();
        fileKeys.add(fileKey);
        ArrayList<String> fileNames = new ArrayList<>();
        fileNames.add(file.getName());
        ArrayList<File> files = new ArrayList<>();
        files.add(file);

        uploadFile(uploadUrl, paramsMap, fileKeys, fileNames, files, callback, uploadFileTag, what);
    }

    @Override
    public void uploadFiles(String uploadUrl, HashMap<String, String> paramsMap,
                            ArrayList<String> fileKeys, ArrayList<String> fileNames, ArrayList<File> files,
                            Object callback, Object uploadFileTag, int... what) {
        uploadFile(uploadUrl, paramsMap, fileKeys, fileNames, files, callback, uploadFileTag, what);
    }


    public Request uploadFile(String uploadUrl, HashMap<String, String> paramsMap,
                              ArrayList<String> fileKeys, ArrayList<String> fileNames, ArrayList<File> files,
                              Object callback, Object uploadFileTag, int... what) {

        ArrayList<FileBinary> fileBinaries = new ArrayList<>();
        for (int i = 0; i < fileKeys.size(); i++) {
            fileBinaries.add(new FileBinary(files.get(i)));
        }
        return uploadFile(uploadUrl, paramsMap,
                fileKeys, fileNames, files, fileBinaries,
                callback, uploadFileTag, what);
    }

    /**
     * 需要文件上传进度显示控制的
     *
     * @param uploadUrl     文件上传路径
     * @param paramsMap     如果需要带参 数
     * @param fileKeys      文件对应带key
     * @param fileNames     文件的名字
     * @param files         文件
     * @param fileBinaries  NoHttp文件上传类
     * @param callback      文件上传回调
     * @param uploadFileTag 文件上传请求tag
     * @param what          请求标识
     * @return
     */
    public Request uploadFile(String uploadUrl, HashMap<String, String> paramsMap,
                              ArrayList<String> fileKeys, ArrayList<String> fileNames, ArrayList<File> files,
                              ArrayList<FileBinary> fileBinaries,
                              Object callback, Object uploadFileTag, int... what) {
        Request<String> request = NoHttp.createStringRequest(uploadUrl, RequestMethod.POST);
        Set<String> key = paramsMap.keySet();
        for (Iterator<String> it = key.iterator(); it.hasNext(); ) {
            String s = it.next();
            request.add(s, paramsMap.get(s));
        }

        for (int i = 0; i < fileKeys.size(); i++) {
            request.add(fileKeys.get(i), fileBinaries.get(i));
        }

        request.setTag(uploadFileTag);
        request.setCancelSign(uploadFileTag);

        requestQueue.add(what[0], request, (OnResponseListener) callback);
        return request;
    }

    @Override
    public void downLoadFile(String downUrl, String savePath, String saveFileName,
                             long connTimeOut, long readTimeOut, long writeTimeOut,
                             Object callBack, Object downFileTag, int... what) {
        downLoadFile(downUrl,
                savePath, saveFileName, connTimeOut, readTimeOut,
                false, false,
                callBack, downFileTag, what);
    }

    /**
     * 支持断点续传
     */
    public DownloadRequest downLoadFile(String downUrl, String savePath, String saveFileName,
                                        long connTimeOut, long readTimeOut,
                                        boolean isRange, boolean isDeleteOld,
                                        Object callBack, Object downFileTag, int... what) {
        initDownloadQueue();
        DownloadRequest downloadRequest = NoHttp.createDownloadRequest(downUrl, savePath, saveFileName, isRange, isDeleteOld);
        downloadRequest.setCancelSign(downFileTag);
        downloadRequest.setTag(downFileTag);
        downloadRequest.setConnectTimeout(new Long(connTimeOut).intValue());
        downloadRequest.setReadTimeout(new Long(readTimeOut).intValue());
        downloadQueue.add(what[0], downloadRequest, (DownloadListener) callBack);
        return downloadRequest;
    }

    @Override
    public boolean isCallBackType(Object callback) {
        if (callback instanceof OnResponseListener) {
            return true;
        } else if (callback instanceof DownloadListener) {
            return true;
        } else {
            throw new IllegalArgumentException("callback what's this?\n" +
                    "please extends com.yolanda.nohttp.OnResponseListener or" +
                    "com.yolanda.nohttp.download.DownloadListener");
        }
    }

    @Override
    public void cancel(Object tag) {
        requestQueue.cancelBySign(tag);
        downloadQueue.cancelBySign(tag);
    }

    public void cancelAllNetWork() {
        requestQueue.cancelAll();
        downloadQueue.cancelAll();
    }


    public Request netWork(HashMap<String, String> paramsMap, String url,
                           RequestMethod method,
                           Object callback, Object tag, int... what) {
        Request<String> request = NoHttp.createStringRequest(url, method);
        Set<String> key = paramsMap.keySet();
        for (Iterator<String> it = key.iterator(); it.hasNext(); ) {
            String s = it.next();
            request.add(s, paramsMap.get(s));
        }
        request.setCancelSign(tag);
        request.setTag(tag);
        netWork(request, callback, what);
        return request;
    }

    public void netWork(Request request, Object callback, int... what) {
        OnResponseListener onResponseListener = (OnResponseListener) callback;
        requestQueue.add(what[0], request, onResponseListener);
    }


}

