package com.zengcanxiang.network;

import android.app.Application;
import android.webkit.URLUtil;

import com.alibaba.fastjson.JSONObject;
import com.zengcanxiang.network.NetWorkCallback.NetWorkCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * NetWorkUtils入口类
 */
public class NetWorkUtil<T> {

    private static NetWork defaultNetWork;

    public static void init(Application application, NetWork network) {
        setNetWork(network);
        defaultNetWork.init(application);
    }

    public static void setNetWork(NetWork network) {
        defaultNetWork = network;
    }

    public static <NW extends NetWork> NW getNetWork(Class<NW> cls) {
        return (NW) defaultNetWork;
    }

    // 超时的相关的时间值
    public static final long TIME_OUT_DEFAULT_CONN = 20000;
    public static final long TIME_OUT_DEFAULT_READ = 20000;
    public static final long TIME_OUT_DEFAULT_WRITE = 20000;

    /**
     * post方式请求
     *
     * @param paramsMap 参数键值对
     * @param url       请求url
     * @param tag       请求tag
     * @param callback  请求回调
     * @param what      当使用noHttp等网络层需要请求what时传入,OkHttp不需要
     *                  类似handler的what一样，这里用来区分请求
     */
    public static void post(String url, HashMap<String, String> paramsMap,
                            NetWorkCallback callback, Object tag, int... what) {
        post(url, paramsMap,
                TIME_OUT_DEFAULT_CONN, TIME_OUT_DEFAULT_READ, TIME_OUT_DEFAULT_WRITE,
                callback, tag, what);
    }

    /**
     * post方式请求，需要更换默认超时时间
     *
     * @param paramsMap    参数键值对
     * @param url          请求url
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写入超时时间
     * @param callback     请求回调
     * @param tag          请求tag
     * @param what         当使用noHttp等网络层需要请求what时传入,OkHttp不需要
     *                     类似handler的what一样，这里用来区分请求
     */
    public static void post(String url, HashMap<String, String> paramsMap,
                            long connTimeOut, long readTimeOut, long writeTimeOut,
                            NetWorkCallback callback, Object tag, int... what) {
        catchUrl(url);
        defaultNetWork.post(url, paramsMap,
                connTimeOut, readTimeOut, writeTimeOut,
                callback, tag, what);
    }

    /**
     * post方式请求,post一个json字符串到服务器
     *
     * @param paramsJSON json
     * @param url        请求url
     * @param callback   请求回调
     * @param tag        请求tag
     * @param what       当使用noHttp等网络层需要请求what时传入,OkHttp不需要
     *                   类似handler的what一样，这里用来区分请求
     */
    public static void post(String url, JSONObject paramsJSON, NetWorkCallback callback, Object tag, int... what) {
        post(url, paramsJSON,
                TIME_OUT_DEFAULT_CONN, TIME_OUT_DEFAULT_READ, TIME_OUT_DEFAULT_WRITE,
                callback, tag, what);
    }

    /**
     * post方式请求,post一个json字符串到服务器，需要更换默认超时时间
     *
     * @param paramsJSON   json
     * @param url          请求url
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写入超时时间
     * @param callback     请求回调
     * @param tag          请求tag
     * @param what         当使用noHttp等网络层需要请求what时传入,OkHttp不需要
     *                     类似handler的what一样，这里用来区分请求
     */
    public static void post(String url, JSONObject paramsJSON,
                            long connTimeOut, long readTimeOut, long writeTimeOut,
                            NetWorkCallback callback, Object tag, int... what) {
        catchUrl(url);
        defaultNetWork.post(url, paramsJSON,
                connTimeOut, readTimeOut, writeTimeOut,
                callback, tag, what);
    }

    /**
     * get方式请求
     *
     * @param paramsMap 参数键值对
     * @param url       请求url
     * @param callback  请求回调
     * @param tag       请求tag
     * @param what      当使用noHttp等网络层需要请求what时传入,OkHttp不需要
     *                  类似handler的what一样，这里用来区分请求
     */
    public static void get(String url, HashMap<String, String> paramsMap,
                           NetWorkCallback callback, Object tag, int... what) {
        get(url, paramsMap,
                TIME_OUT_DEFAULT_CONN, TIME_OUT_DEFAULT_READ, TIME_OUT_DEFAULT_WRITE,
                callback, tag, what);
    }

    /**
     * get方式请求,需要更换默认超时时间
     *
     * @param paramsMap    参数键值对
     * @param url          请求url
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写入超时时间
     * @param callback     请求回调
     * @param tag          请求tag
     * @param what         当使用noHttp等网络层需要请求what时传入,OkHttp不需要
     *                     类似handler的what一样，这里用来区分请求
     */
    public static void get(String url, HashMap<String, String> paramsMap,
                           long connTimeOut, long readTimeOut, long writeTimeOut,
                           NetWorkCallback callback, Object tag, int... what) {
        catchUrl(url);
        defaultNetWork.get(url, paramsMap,
                connTimeOut, readTimeOut, writeTimeOut,
                callback, tag, what);
    }


    /**
     * 上传文件
     *
     * @param uploadUrl     上传url
     * @param paramsMap     键值对
     * @param fileKey       文件key
     * @param file          文件
     * @param callback      上传回调
     * @param uploadFileTag 上传文件请求tag
     * @param what          当使用noHttp等网络层需要请求what时传入,OkHttp不需要
     *                      类似handler的what一样，这里用来区分请求
     */
    public static void uploadFile(String uploadUrl,
                                  HashMap<String, String> paramsMap, String fileKey, File file,
                                  NetWorkCallback callback, Object uploadFileTag, int... what) {
        catchUrl(uploadUrl);
        defaultNetWork.uploadFile(uploadUrl, paramsMap,
                fileKey, file,
                callback, uploadFileTag, what);
    }
    /**
     * 上传多个文件
     *
     * @param uploadUrl     上传url
     * @param paramsMap     上传同时带的参数
     * @param fileKeys      文件分别对应的key
     * @param fileNames     文件分别对应的name
     * @param files         文件
     * @param callback      请求回调
     * @param uploadFileTag 上传文件请求tag
     * @param what          当使用noHttp等网络层需要请求what时传入,OkHttp不需要
     *                      类似handler的what一样，这里用来区分请求
     */
    public static void uploadFiles(String uploadUrl, HashMap<String, String> paramsMap,
                                   ArrayList<String> fileKeys, ArrayList<String> fileNames,
                                   ArrayList<File> files, NetWorkCallback callback,
                                   Object uploadFileTag, int... what) {
        catchUrl(uploadUrl);
        defaultNetWork.uploadFiles(uploadUrl, paramsMap,
                fileKeys, fileNames, files,
                callback, uploadFileTag, what);
    }

    /**
     * 上传文件,不需要其他参数
     *
     * @param uploadUrl     上传url
     * @param uploadFileTag 上传文件请求tag
     * @param file          文件
     * @param callback      上传回调
     * @param what          当使用noHttp等网络层需要请求what时传入,OkHttp不需要
     *                      类似handler的what一样，这里用来区分请求
     */
    public static void uploadFile(String uploadUrl, Object uploadFileTag,
                                  File file, NetWorkCallback callback, int... what) {
        uploadFile(uploadUrl, new HashMap<String, String>(), "", file, callback, uploadFileTag, what);
    }


    /**
     * 下载文件,使用默认超时时间
     *
     * @param downUrl      下载url
     * @param downFileTag  下载请求tag
     * @param savePath     保存路径
     * @param saveFileName 保存的文件名称
     * @param callBack     请求回调
     * @param what         当使用noHttp等网络层需要请求what时传入,OkHttp不需要
     *                     类似handler的what一样，这里用来区分请求
     */
    public static void downFile(String downUrl, Object downFileTag,
                                String savePath, String saveFileName,
                                NetWorkCallback callBack, int... what) {
        downFile(downUrl, downFileTag, savePath, saveFileName,
                TIME_OUT_DEFAULT_CONN, TIME_OUT_DEFAULT_READ, TIME_OUT_DEFAULT_WRITE,
                callBack, what);
    }

    /**
     * 下载文件,有时间方面要求的
     *
     * @param downUrl      下载url
     * @param downFileTag  下载请求tag
     * @param savePath     保存路径
     * @param saveFileName 保存的文件名称
     * @param connTimeOut  连接超时限制
     * @param readTimeOut  读取超时限制
     * @param writeTimeOut 写操作超时限制
     * @param callback     请求回调
     * @param what         当使用noHttp等网络层需要请求what时传入,OkHttp不需要
     *                     类似handler的what一样，这里用来区分请求
     */
    public static void downFile(String downUrl, Object downFileTag,
                                String savePath, String saveFileName,
                                long connTimeOut, long readTimeOut, long writeTimeOut,
                                NetWorkCallback callback, int... what) {
        catchUrl(downUrl);
        defaultNetWork.downLoadFile(downUrl, savePath, saveFileName,
                connTimeOut, readTimeOut, writeTimeOut,
                callback, downFileTag, what);
    }


    /**
     * 取消某个tag对应的请求
     *
     * @param tag 请求对应的tag
     */
    public static void cancelTag(Object tag) {
        defaultNetWork.cancel(tag);
    }

    private static void catchUrl(String url) {

        if(!URLUtil.isNetworkUrl(url)){
            throw new IllegalArgumentException("url不符合规则 没有http或者https前缀");
        }
    }

}
