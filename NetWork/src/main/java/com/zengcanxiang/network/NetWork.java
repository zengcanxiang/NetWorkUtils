package com.zengcanxiang.network;

import com.zengcanxiang.network.NetWorkCallback.NetWorkCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zengcanxiang on 2016/2/26.
 */
public abstract class NetWork<T> {

    /**
     * 网络层的post请求方法
     *
     * @param paramsMap 请求需要的参数集
     * @param url       请求url
     * @param callback  请求回调
     * @param tag       请求tag
     * @param what      请求标识,NoHttp需要
     */
    public abstract void post(HashMap<String, String> paramsMap, String url,
                              NetWorkCallback<T> callback, Object tag, int... what);

    /**
     * 网络层的get请求方法
     *
     * @param paramsMap 请求需要的参数集
     * @param url       请求url
     * @param callback  请求回调
     * @param tag       请求tag
     * @param what      请求标识,NoHttp需要
     */
    public abstract void get(HashMap<String, String> paramsMap, String url,
                             NetWorkCallback<T> callback, Object tag, int... what);

    /**
     * 网络层上传参数和单个文件
     *
     * @param uploadUrl     上传url
     * @param paramsMap     上传同时需要带的参数集
     * @param fileKey       文件key
     * @param file          文件
     * @param callback      上传回调
     * @param uploadFileTag 上传文件tag
     * @param what          请求标识,NoHttp需要
     */
    public abstract void uploadFile(String uploadUrl,
                                    HashMap<String, String> paramsMap,
                                    String fileKey, File file,
                                    NetWorkCallback<T> callback, Object uploadFileTag, int... what);


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
     * @param what          请求标识,NoHttp需要
     */
    public abstract void uploadFiles(String uploadUrl, HashMap<String, String> paramsMap,
                                     ArrayList<String> fileKeys, ArrayList<String> fileNames,
                                     ArrayList<File> files, NetWorkCallback<T> callback, Object uploadFileTag, int... what);

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
     * @param what         请求标识,NoHttp需要
     */
    public abstract void downLoadFile(String downUrl, String savePath, String saveFileName,
                                      long connTimeOut, long readTimeOut, long writeTimeOut,
                                      NetWorkCallback<T> callBack, Object downFileTag, int... what);

    /**
     * 取消tag对应的请求
     *
     * @param tag 请求tag
     */
    public abstract void cancel(Object tag);

}
