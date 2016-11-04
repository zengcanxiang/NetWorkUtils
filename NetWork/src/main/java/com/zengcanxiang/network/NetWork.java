package com.zengcanxiang.network;

import android.app.Application;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * 兼容网络框架之间不同风格的请求规范
 */
public abstract class NetWork<R extends NetWorkCallback> {

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
     * @param tag          请求tag
     * @param callback     请求回调
     */
    public abstract void post(String url, String paramsJSON,
                              long connTimeOut, long readTimeOut, long writeTimeOut,
                              String tag, R callback);

    /**
     * 网络层的post请求方法,post
     *
     * @param paramsMap    请求需要的参数集
     * @param url          请求url
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写入超时时间
     * @param tag          请求tag
     * @param callback     请求回调
     */
    public abstract void post(String url, HashMap<String, String> paramsMap,
                              long connTimeOut, long readTimeOut, long writeTimeOut,
                              String tag, R callback);

    /**
     * 网络层的get请求方法
     *
     * @param paramsMap    请求需要的参数集
     * @param url          请求url
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写入超时时间
     * @param tag          请求tag
     * @param callback     请求回调
     */
    public abstract void get(String url, HashMap<String, String> paramsMap,
                             long connTimeOut, long readTimeOut, long writeTimeOut,
                             String tag, R callback);

    /**
     * 网络层上传参数和单个文件
     *
     * @param uploadUrl     上传url
     * @param paramsMap     上传同时需要带的参数集
     * @param fileKey       文件key
     * @param file          文件
     * @param connTimeOut   连接超时时间
     * @param readTimeOut   读取超时时间
     * @param writeTimeOut  写入超时时间
     * @param uploadFileTag 上传文件tag
     * @param callback      上传回调
     */
    public abstract void uploadFile(String uploadUrl,
                                    HashMap<String, String> paramsMap,
                                    String fileKey, File file,
                                    long connTimeOut, long readTimeOut, long writeTimeOut,
                                    String uploadFileTag, R callback);

    /**
     * 网络层上传参数和多个文件
     *
     * @param uploadUrl     上传url
     * @param paramsMap     上传同时需要带的参数集
     * @param fileKeys      文件keys
     * @param files         文件s
     * @param connTimeOut   连接超时时间
     * @param readTimeOut   读取超时时间
     * @param writeTimeOut  写入超时时间
     * @param uploadFileTag 上传文件tag
     * @param callback      上传回调
     */
    public abstract void uploadFile(String uploadUrl, HashMap<String, String> paramsMap,
                                    List<String> fileKeys, List<File> files,
                                    long connTimeOut, long readTimeOut, long writeTimeOut,
                                    String uploadFileTag, R callback);

    /**
     * 网络层下载文件
     *
     * @param downUrl      文件下载url
     * @param savePath     文件保存路径
     * @param saveFileName 文件保存名称
     * @param connTimeOut  下载链接超时时间
     * @param readTimeOut  读取超时时间
     * @param writeTimeOut 写超时时间
     * @param downFileTag  下载文件tag
     * @param callBack     下载回调
     */
    public abstract void downFile(String downUrl, String savePath, String saveFileName,
                                  long connTimeOut, long readTimeOut, long writeTimeOut,
                                  String downFileTag, DownCallback callBack);

    /**
     * 取消tag对应的请求
     *
     * @param tag 请求tag
     */
    public abstract void cancel(String tag);

    /**
     * 取消tag对应的下载请求
     *
     * @param tag 请求tag
     */
    public abstract void cancelDown(String tag);

    /**
     * 取消tag对应的上传请求
     *
     * @param tag 请求tag
     */
    public abstract void cancelUpload(String tag);

    /**
     * 取消所有请求
     */
    public abstract void cancelAll();
}
