package com.zengcanxiang.network;

import android.app.Application;

import com.alibaba.fastjson.JSONObject;
import com.zengcanxiang.network.NetWorkCallback.NetWorkCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 兼容网络框架之间不同风格的请求规范
 */
public abstract class NetWork<T> {

    /**
     * 网络框架初始化
     *
     * @param application 应用上下文
     */
    public abstract void init(Application application);

    /**
     * 网络层的post请求方法，post一个json字符串到服务器
     *
     * @param paramsJSON   json
     * @param url          请求url
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写入超时时间
     * @param callback     请求回调
     * @param tag          请求tag
     * @param NoHttpWhat   请求标识,NoHttp需要
     */
    public abstract void post(String url, JSONObject paramsJSON,
                              long connTimeOut, long readTimeOut, long writeTimeOut,
                              NetWorkCallback<T> callback, Object tag, int... NoHttpWhat);

    /**
     * 网络层的post请求方法
     *
     * @param paramsMap    请求需要的参数集
     * @param url          请求url
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写入超时时间
     * @param callback     请求回调
     * @param tag          请求tag
     * @param NoHttpWhat   请求标识,NoHttp需要
     */
    public abstract void post(String url, HashMap<String, String> paramsMap,
                              long connTimeOut, long readTimeOut, long writeTimeOut,
                              NetWorkCallback<T> callback, Object tag, int... NoHttpWhat);

    /**
     * 网络层的get请求方法
     *
     * @param paramsMap    请求需要的参数集
     * @param url          请求url
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写入超时时间
     * @param callback     请求回调
     * @param tag          请求tag
     * @param NoHttpWhat   请求标识,NoHttp需要
     */
    public abstract void get(String url, HashMap<String, String> paramsMap,
                             long connTimeOut, long readTimeOut, long writeTimeOut,
                             NetWorkCallback<T> callback, Object tag, int... NoHttpWhat);

    /**
     * 网络层上传参数和单个文件
     *
     * @param uploadUrl     上传url
     * @param paramsMap     上传同时需要带的参数集
     * @param fileKey       文件key
     * @param file          文件
     * @param callback      上传回调
     * @param uploadFileTag 上传文件tag
     * @param NoHttpWhat    请求标识,NoHttp需要
     */
    public abstract void uploadFile(String uploadUrl,
                                    HashMap<String, String> paramsMap,
                                    String fileKey, File file,
                                    NetWorkCallback<T> callback, Object uploadFileTag, int... NoHttpWhat);
    /**
     * 网络层上传参数和多个文件
     *
     * @param uploadUrl     上传url
     * @param paramsMap     上传同时需要带的参数集
     * @param fileKeys      文件keys
     * @param fileNames     文件names,okHttp需要
     * @param files         文件s
     * @param callback      上传回调
     * @param uploadFileTag 上传文件tag
     * @param NoHttpWhat    请求标识,NoHttp需要
     */
    public abstract void uploadFiles(String uploadUrl, HashMap<String, String> paramsMap,
                                     ArrayList<String> fileKeys, ArrayList<String> fileNames,
                                     ArrayList<File> files, NetWorkCallback<T> callback, Object uploadFileTag, int... NoHttpWhat);

    /**
     * 网络层下载文件
     *
     * @param downUrl      文件下载url
     * @param savePath     文件保存路径
     * @param saveFileName 文件保存名称
     * @param connTimeOut  下载链接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写超时时间
     * @param callBack     下载回调
     * @param downFileTag  下载文件tag
     * @param NoHttpWhat   请求标识,NoHttp需要
     */
    public abstract void downLoadFile(String downUrl, String savePath, String saveFileName,
                                      long connTimeOut, long readTimeOut, long writeTimeOut,
                                      NetWorkCallback<T> callBack, Object downFileTag, int... NoHttpWhat);

    /**
     * 取消tag对应的请求
     *
     * @param tag 请求tag
     */
    public abstract void cancel(Object tag);

}
