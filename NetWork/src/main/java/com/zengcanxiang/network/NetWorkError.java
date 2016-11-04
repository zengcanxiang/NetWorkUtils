package com.zengcanxiang.network;

/**
 * 请求错误信息父类,不限制子类，只是作为一个总类
 */
public class NetWorkError {
    /**
     * 文本错误信息
     */
    private String errorMsg;
    /**
     * 原始错误异常
     */
    private Exception errorException;

    public NetWorkError() {
    }

    public NetWorkError(String errorMsg) {
        this.errorMsg = errorMsg;
        errorException = new IllegalArgumentException(errorMsg);
    }


    public NetWorkError(String errorMsg, Exception e) {
        this.errorMsg = errorMsg;
        this.errorException = e;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Exception getException() {
        return errorException;
    }

    public void setException(Exception exception) {
        this.errorException = exception;
    }
}
