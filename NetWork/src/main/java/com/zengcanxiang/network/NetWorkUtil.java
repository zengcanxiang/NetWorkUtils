package com.zengcanxiang.network;

import com.yolanda.nohttp.error.ArgumentError;
import com.yolanda.nohttp.error.ClientError;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.StorageReadWriteError;
import com.yolanda.nohttp.error.StorageSpaceNotEnoughError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.zengcanxiang.network.OkHttpNetWork.OkHttpNetWork;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zengcanxiang on 2016/2/26.
 */
public class NetWorkUtil {

    private static boolean debug = false;

    static {
        Object debugType = BuildConfig.DEBUG;
        if (debugType instanceof Boolean) {
            debug = BuildConfig.DEBUG;
        }
        if (debugType instanceof String) {
            switch (debugType.toString()) {
                case "debug":
                    debug = true;
                    break;
                default:
                    debug = false;
                    break;
            }
        }
    }

    private static NetWork defaultNetWork = new OkHttpNetWork();

    public static void setNetWork(NetWork network) {
        defaultNetWork = network;
    }

    public static <T extends NetWork> T getNetWork(Class<T> cls) {
        return (T) defaultNetWork;
    }

    /**
     * <p>
     * 如果没有为请求自定义tag，即使用默认tag
     * </p>
     * <p>
     * 不推荐,因为可能会存在取消某一个请求的时候,会取消掉其他为默认tag但是不想取消的请求
     * </p>
     *
     * @deprecated
     */
    public static final String DEFAULT_TAG = "Framework";

    // 相关的默认时间值
    public static final long TIME_OUT_CONN_DEFAULT = 20000;
    public static final long TIME_OUT_READ_DEFAULT = 20000;
    public static final long TIME_OUT_WRITE_DEFAULT = 20000;

    /**
     * post方式请求
     *
     * @param paramsMap 参数键值对
     * @param url       请求url
     * @param tag       请求tag
     * @param callback  请求回调
     * @param what      当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                  类似handler的what一样，这里用来区分请求
     */
    public static void post(HashMap<String, String> paramsMap, String url,
                            Object tag, Object callback, int... what) throws IllegalArgumentException {
        catchUrl(url);
        if (defaultNetWork.isCallBackType(callback)) {
            defaultNetWork.post(paramsMap, url, tag, callback, what);
        }
    }

    /**
     * post方式请求,使用默认的tag
     *
     * @param paramsMap 请求url
     * @param url       请求tag
     * @param callback  请求回调
     * @param what      当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                  类似handler的what一样，这里用来区分请求
     */
    public static void post(HashMap<String, String> paramsMap, String url,
                            Object callback, int... what) {
        post(paramsMap, url, DEFAULT_TAG, callback, what);
    }

    /**
     * get方式请求
     *
     * @param paramsMap 参数键值对
     * @param url       请求url
     * @param tag       请求tag
     * @param callback  请求回调
     * @param what      当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                  类似handler的what一样，这里用来区分请求
     */
    public static void get(HashMap<String, String> paramsMap, String url,
                           Object tag, Object callback, int... what) {
        catchUrl(url);
        if (defaultNetWork.isCallBackType(callback)) {
            defaultNetWork.get(paramsMap, url, tag, callback, what);
        }
    }

    /**
     * get方式请求,使用默认的tag
     *
     * @param paramsMap 参数键值对
     * @param url       请求url
     * @param callback  请求回调
     * @param what      当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                  类似handler的what一样，这里用来区分请求
     */
    public static void get(HashMap<String, String> paramsMap, String url,
                           Object callback, int... what) {
        get(paramsMap, url, DEFAULT_TAG, callback, what);
    }

    /**
     * 上传文件,没有测试大文件上传是否可行
     *
     * @param uploadUrl     上传url
     * @param uploadFileTag 上传文件请求tag
     * @param paramsMap     键值对
     * @param file          文件
     * @param callback      上传回调
     * @param what          当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                      类似handler的what一样，这里用来区分请求
     */
    public static void uploadFile(String uploadUrl, Object uploadFileTag,
                                  HashMap<String, String> paramsMap, String fileKey, File file,
                                  Object callback, int... what) {
        catchUrl(uploadUrl);
        if (defaultNetWork.isCallBackType(callback)) {
            defaultNetWork.uploadFile(uploadUrl, paramsMap,
                    fileKey, file,
                    callback, uploadFileTag, what);
        }
    }

    /**
     * 上传文件,不需要键值对,没有测试大文件上传是否可行,
     *
     * @param uploadUrl     上传url
     * @param uploadFileTag 上传文件请求tag
     * @param file          文件
     * @param callback      上传回调
     * @param what          当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                      类似handler的what一样，这里用来区分请求
     */
    public static void uploadFile(String uploadUrl, Object uploadFileTag,
                                  File file, Object callback, int... what) {
        uploadFile(uploadUrl, uploadFileTag, new HashMap<String, String>(), "", file, callback, what);
    }

    /**
     * 表单形式上传单个文件
     *
     * @param uploadUrl     上传url
     * @param uploadFileTag 上传文件请求tag
     * @param paramsMap     键值对
     * @param fileKey       文件对应的key
     * @param fileName      文件的名称
     * @param file          文件
     * @param callback      请求回调
     * @param what          当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                      类似handler的what一样，这里用来区分请求
     */
    public static void uploadFile(String uploadUrl, Object uploadFileTag,
                                  HashMap<String, String> paramsMap, String fileKey, String fileName,
                                  File file, Object callback, int... what) {
        catchUrl(uploadUrl);
        if (defaultNetWork.isCallBackType(callback)) {
            ArrayList<String> fileKeys = new ArrayList<String>();
            fileKeys.add(fileKey);
            ArrayList<String> fileNames = new ArrayList<String>();
            fileNames.add(fileName);
            ArrayList<File> files = new ArrayList<File>();
            files.add(file);
            defaultNetWork.uploadFiles(uploadUrl, paramsMap,
                    fileKeys, fileNames, files, callback, uploadFileTag, what);
        }
    }

    /**
     * 表单形式上传单个文件,不需要键值对
     *
     * @param uploadUrl     上传url
     * @param uploadFileTag 上传文件请求tag
     * @param fileKey       文件对应的key
     * @param fileName      文件的名称
     * @param file          文件
     * @param callback      请求回调
     * @param what          当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                      类似handler的what一样，这里用来区分请求
     */
    public static void uploadFile(String uploadUrl, Object uploadFileTag,
                                  String fileKey, String fileName, File file,
                                  Object callback, int... what) {
        uploadFile(uploadUrl, uploadFileTag, null, fileKey, fileName, file,
                callback, what);
    }

    /**
     * 表单形式上传多个文件
     *
     * @param uploadUrl     上传url
     * @param uploadFileTag 上传文件请求tag
     * @param fileKeys      文件分别对应的key
     * @param fileNames     文件分别对应的name
     * @param files         文件
     * @param paramsMap     键值对
     * @param callback      请求回调
     * @param what          当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                      类似handler的what一样，这里用来区分请求
     */
    public static void uploadFiles(String uploadUrl, Object uploadFileTag,
                                   ArrayList<String> fileKeys, ArrayList<String> fileNames,
                                   ArrayList<File> files, HashMap<String, String> paramsMap,
                                   Object callback, int... what) {
        catchUrl(uploadUrl);
        if (defaultNetWork.isCallBackType(callback)) {
            defaultNetWork.uploadFiles(uploadUrl, paramsMap,
                    fileKeys, fileNames, files, callback, uploadFileTag, what);
        }
    }

    /**
     * 表单形式上传多个文件,不需要键值对
     *
     * @param uploadUrl     上传url
     * @param uploadFileTag 上传文件请求tag
     * @param fileKeys      文件分别对应的key
     * @param fileNames     文件分别对应的name
     * @param files         文件
     * @param callback      请求回调
     * @param what          当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                      类似handler的what一样，这里用来区分请求
     */
    public static void uploadFiles(String uploadUrl, Object uploadFileTag,
                                   ArrayList<String> fileKeys, ArrayList<String> fileNames,
                                   ArrayList<File> files, Object callback, int... what) {
        uploadFiles(uploadUrl, uploadFileTag, fileKeys, fileNames, files, null,
                callback, what);
    }

    /**
     * 下载文件,使用默认超时时间
     *
     * @param downUrl      下载url
     * @param downFileTag  下载请求tag
     * @param savePath     保存路径
     * @param saveFileName 保存的文件名称
     * @param callBack     请求回调
     * @param what         当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                     类似handler的what一样，这里用来区分请求
     */
    public static void downFile(String downUrl, Object downFileTag,
                                String savePath, String saveFileName,
                                Object callBack, int... what) {
        downFile(downUrl, downFileTag, savePath, saveFileName,
                TIME_OUT_CONN_DEFAULT, TIME_OUT_READ_DEFAULT, TIME_OUT_WRITE_DEFAULT,
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
     * @param what         当使用noHttp等网络层需要请求what时传入,默认OkHttp不需要
     *                     类似handler的what一样，这里用来区分请求
     */
    public static void downFile(String downUrl, Object downFileTag,
                                String savePath, String saveFileName,
                                long connTimeOut, long readTimeOut, long writeTimeOut,
                                Object callback, int... what) {
        catchUrl(downUrl);
        if (defaultNetWork.isCallBackType(callback)) {
            defaultNetWork.downLoadFile(downUrl, savePath, saveFileName,
                    connTimeOut, readTimeOut, writeTimeOut,
                    callback, downFileTag, what);
        }
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
        if (url != null && url.indexOf("http") < 0) {
            throw new IllegalArgumentException("url不符合规则 没有http或者https前缀");
        }
    }

    /**
     * 判断网络异常是什么
     *
     * @param exception
     * @return
     */
    public static String defaultExceptionMessage(Exception exception) {
        StringBuffer message = new StringBuffer("出错了：");
        if (exception instanceof ClientError) {
            message.append("客户端错误");
        } else if (exception instanceof ServerError) {
            message.append("服务器发生内部错误");
        } else if (exception instanceof NetworkError) {
            message.append("网络不可用，请检查网络");
        } else if (exception instanceof StorageReadWriteError) {
            message.append("存储卡错误，请检查存储卡");
        } else if (exception instanceof StorageSpaceNotEnoughError) {
            message.append("存储位置空间不足");
        } else if (exception instanceof TimeoutError) {
            message.append("超时");
        } else if (exception instanceof UnKnownHostError) {
            message.append("服务器找不到");
        } else if (exception instanceof URLError) {
            message.append("url地址错误");
        } else if (exception instanceof ArgumentError) {
            message.append("参数错误");
        } else {
            message.append("未知错误");
        }
        return message.toString();
    }
}
