package com.zengcanxiang.network;

import android.app.Application;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * NetWorkUtils入口类
 */
public final class NetWorkUtil {

    private static NetWork defaultNetWork;

    public static void init(Application app, Class netWorkClz) {
        if (netWorkClz == null) {
            throw new IllegalArgumentException("init netWorkClz is null");
        }
        try {
            init(app, (NetWork) netWorkClz.newInstance());
        } catch (Exception e) {
            Log.e("NetWorkUtil", "netWork init error");
            throw new IllegalArgumentException("netWork init error :" + e.getMessage());
        }
    }

    public static void init(Application app, NetWork netWork) {
        if (netWork == null) {
            throw new IllegalArgumentException("init network is null");
        }
        setNetWork(netWork);
        defaultNetWork.init(app);
    }

    public static void setNetWork(NetWork network) {
        defaultNetWork = network;
    }

    public static <NW extends NetWork> NW getNetWork(Class<NW> cls) {
        return (NW) defaultNetWork;
    }

    // 超时的相关的时间值
    public static final long TIME_OUT_DEFAULT_CONN = 1000 * 10 * 2;
    public static final long TIME_OUT_DEFAULT_READ = 1000 * 10 * 2;
    public static final long TIME_OUT_DEFAULT_WRITE = 1000 * 10 * 2;

    /**
     * post方式请求
     *
     * @param paramsMap 参数键值对
     * @param url       请求url
     * @param tag       请求tag
     * @param callback  请求回调
     */
    public static void post(String url, HashMap<String, String> paramsMap,
                            String tag, NetWorkCallback callback) {
        post(url, paramsMap,
                TIME_OUT_DEFAULT_CONN, TIME_OUT_DEFAULT_READ, TIME_OUT_DEFAULT_WRITE,
                tag, callback);
    }

    /**
     * post方式请求，需要更换默认超时时间
     *
     * @param paramsMap    参数键值对
     * @param url          请求url
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写入超时时间
     * @param tag          请求tag
     * @param callback     请求回调
     */
    public static void post(String url, HashMap<String, String> paramsMap,
                            long connTimeOut, long readTimeOut, long writeTimeOut,
                            String tag, NetWorkCallback callback) {
        if (catchUrl(url)) {
            defaultNetWork.post(url, paramsMap,
                    connTimeOut, readTimeOut, writeTimeOut,
                    tag, callback);
        } else {
            callback.onError(new NetWorkError("url不符合规则 没有http或者https前缀"));
        }
    }

    /**
     * post方式请求,post一个json字符串到服务器
     *
     * @param paramsJSON json
     * @param url        请求url
     * @param callback   请求回调
     * @param tag        请求tag
     */
    public static void post(String url, String paramsJSON, String tag, NetWorkCallback callback) {
        post(url, paramsJSON,
                TIME_OUT_DEFAULT_CONN, TIME_OUT_DEFAULT_READ, TIME_OUT_DEFAULT_WRITE,
                tag, callback);
    }

    /**
     * post方式请求,post一个json字符串到服务器，需要更换默认超时时间
     *
     * @param paramsJSON   json
     * @param url          请求url
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写入超时时间
     * @param tag          请求tag
     * @param callback     请求回调
     */
    public static void post(String url, String paramsJSON,
                            long connTimeOut, long readTimeOut, long writeTimeOut,
                            String tag, NetWorkCallback callback) {
        if (catchUrl(url)) {
            defaultNetWork.post(url, paramsJSON,
                    connTimeOut, readTimeOut, writeTimeOut,
                    tag, callback);
        } else {
            callback.onError(new NetWorkError("url不符合规则 没有http或者https前缀"));
        }
    }

    /**
     * get方式请求
     *
     * @param paramsMap 参数键值对
     * @param url       请求url
     * @param tag       请求tag
     * @param callback  请求回调
     */
    public static void get(String url, HashMap<String, String> paramsMap,
                           String tag, NetWorkCallback callback) {
        get(url, paramsMap,
                TIME_OUT_DEFAULT_CONN, TIME_OUT_DEFAULT_READ, TIME_OUT_DEFAULT_WRITE,
                tag, callback);
    }

    /**
     * get方式请求,需要更换默认超时时间
     *
     * @param paramsMap    参数键值对
     * @param url          请求url
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写入超时时间
     * @param tag          请求tag
     * @param callback     请求回调
     */
    public static void get(String url, HashMap<String, String> paramsMap,
                           long connTimeOut, long readTimeOut, long writeTimeOut,
                           String tag, NetWorkCallback callback) {
        if (catchUrl(url)) {
            defaultNetWork.get(url, paramsMap,
                    connTimeOut, readTimeOut, writeTimeOut,
                    tag, callback);
        } else {
            callback.onError(new NetWorkError("url不符合规则 没有http或者https前缀"));
        }

    }


    /**
     * 上传文件
     *
     * @param uploadUrl     上传url
     * @param paramsMap     键值对
     * @param fileKey       文件key
     * @param file          文件
     * @param uploadFileTag 上传文件请求tag
     * @param callback      上传回调
     */

    public static void uploadFile(String uploadUrl, HashMap<String, String> paramsMap,
                                  String fileKey, File file,
                                  String uploadFileTag, NetWorkCallback callback) {
        uploadFile(uploadUrl, paramsMap,
                fileKey, file,
                NetWorkUtil.TIME_OUT_DEFAULT_CONN, NetWorkUtil.TIME_OUT_DEFAULT_READ, NetWorkUtil.TIME_OUT_DEFAULT_WRITE,
                uploadFileTag, callback);
    }

    /**
     * 上传文件
     *
     * @param uploadUrl     上传url
     * @param paramsMap     键值对
     * @param fileKey       文件key
     * @param file          文件
     * @param connTimeOut   连接超时时间
     * @param readTimeOut   读取超时时间
     * @param writeTimeOut  写入超时时间
     * @param uploadFileTag 上传文件请求tag
     * @param callback      上传回调
     */
    public static void uploadFile(String uploadUrl, HashMap<String, String> paramsMap,
                                  String fileKey, File file,
                                  long connTimeOut, long readTimeOut, long writeTimeOut,
                                  String uploadFileTag, NetWorkCallback callback) {
        catchUrl(uploadUrl);
        defaultNetWork.uploadFile(uploadUrl, paramsMap,
                fileKey, file, connTimeOut, readTimeOut, writeTimeOut,
                uploadFileTag, callback);
    }

    /**
     * 上传多个文件
     *
     * @param uploadUrl     上传url
     * @param paramsMap     上传同时带的参数
     * @param fileKeys      文件分别对应的keys
     * @param files         文件s
     * @param uploadFileTag 上传文件请求tag
     * @param callback      请求回调
     */
    public static void uploadFile(String uploadUrl, HashMap<String, String> paramsMap,
                                  List<String> fileKeys, List<File> files,
                                  String uploadFileTag, NetWorkCallback callback) {
        uploadFile(uploadUrl, paramsMap,
                fileKeys, files,
                NetWorkUtil.TIME_OUT_DEFAULT_CONN, NetWorkUtil.TIME_OUT_DEFAULT_READ, NetWorkUtil.TIME_OUT_DEFAULT_WRITE,
                uploadFileTag, callback);
    }

    /**
     * 上传多个文件
     *
     * @param uploadUrl     上传url
     * @param paramsMap     上传同时带的参数
     * @param fileKeys      文件分别对应的key
     * @param files         文件
     * @param uploadFileTag 上传文件请求tag
     * @param callback      请求回调
     */
    public static void uploadFile(String uploadUrl, HashMap<String, String> paramsMap,
                                  List<String> fileKeys, List<File> files,
                                  long connTimeOut, long readTimeOut, long writeTimeOut,
                                  String uploadFileTag, NetWorkCallback callback) {
        catchUrl(uploadUrl);
        defaultNetWork.uploadFile(uploadUrl, paramsMap,
                fileKeys, files,
                connTimeOut, readTimeOut, writeTimeOut,
                uploadFileTag, callback);
    }

    /**
     * 上传文件,不需要其他参数
     *
     * @param uploadUrl     上传url
     * @param file          文件
     * @param uploadFileTag 上传文件请求tag
     * @param callback      上传回调
     */
    public static void uploadFile(String uploadUrl,
                                  String fileKey, File file,
                                  String uploadFileTag, NetWorkCallback callback) {
        uploadFile(uploadUrl,
                fileKey, file,
                NetWorkUtil.TIME_OUT_DEFAULT_CONN, NetWorkUtil.TIME_OUT_DEFAULT_READ, NetWorkUtil.TIME_OUT_DEFAULT_WRITE,
                uploadFileTag, callback);
    }

    /**
     * 上传文件,不需要其他参数
     *
     * @param uploadUrl     上传url
     * @param file          文件
     * @param uploadFileTag 上传文件请求tag
     * @param callback      上传回调
     */
    public static void uploadFile(String uploadUrl,
                                  String fileKey, File file,
                                  long connTimeOut, long readTimeOut, long writeTimeOut,
                                  String uploadFileTag, NetWorkCallback callback) {
        uploadFile(uploadUrl,
                new HashMap<String, String>(),
                fileKey, file,
                connTimeOut, readTimeOut, writeTimeOut,
                uploadFileTag, callback);
    }


    /**
     * 下载文件,使用默认超时时间
     *
     * @param downUrl      下载url
     * @param savePath     保存路径
     * @param saveFileName 保存的文件名称
     * @param downFileTag  下载请求tag
     * @param callBack     请求回调
     */
    public static void downFile(String downUrl, String savePath,
                                String saveFileName, String downFileTag,
                                DownCallback callBack) {
        downFile(downUrl, savePath, saveFileName,
                TIME_OUT_DEFAULT_CONN, TIME_OUT_DEFAULT_READ, TIME_OUT_DEFAULT_WRITE,
                downFileTag, callBack);
    }

    /**
     * 下载文件,有时间方面要求的
     *
     * @param downUrl      下载url
     * @param savePath     保存路径
     * @param saveFileName 保存的文件名称
     * @param connTimeOut  连接超时限制
     * @param readTimeOut  读取超时限制
     * @param writeTimeOut 写操作超时限制
     * @param downFileTag  下载请求tag
     * @param callback     请求回调
     */
    public static void downFile(String downUrl, String savePath, String saveFileName,
                                long connTimeOut, long readTimeOut, long writeTimeOut,
                                String downFileTag, DownCallback callback) {
        if (catchUrl(downUrl)) {
            defaultNetWork.downFile(downUrl, savePath, saveFileName,
                    connTimeOut, readTimeOut, writeTimeOut,
                    downFileTag, callback);
        } else {
            callback.onError(new NetWorkError("url不符合规则 没有http或者https前缀"));
        }
    }


    /**
     * 取消某个tag对应的请求
     *
     * @param tag 请求对应的tag
     */
    public static void cancel(String tag) {
        defaultNetWork.cancel(tag);
    }

    /**
     * 取消某个tag对应的下载请求
     *
     * @param tag 请求对应的tag
     */
    public static void cancelDown(String tag) {
        defaultNetWork.cancelDown(tag);
    }

    /**
     * 取消某个tag对应的上传请求
     *
     * @param tag 请求对应的tag
     */
    public static void cancelUpload(String tag) {
        defaultNetWork.cancelUpload(tag);
    }

    /**
     * 取消所有请求
     */
    public static void cancelAll() {
        defaultNetWork.cancelAll();
    }

    private static boolean catchUrl(String url) {
        return URLUtil.isNetworkUrl(url);
    }

}
