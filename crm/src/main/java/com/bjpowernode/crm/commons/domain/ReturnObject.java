package com.bjpowernode.crm.commons.domain;

import com.bjpowernode.crm.settings.domain.User;

/**
 * @author hp
 * @date 2022-11-07 14:34
 * @explain
 */
public class ReturnObject {
    private String code;
    private String message;
    private Object retObject;

    public ReturnObject() {
    }

    @Override
    public String toString() {
        return "ReturnObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", retObject=" + retObject +
                '}';
    }

    public ReturnObject(String code, String message, Object retObject) {
        this.code = code;
        this.message = message;
        this.retObject = retObject;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetObject() {
        return retObject;
    }

    public void setRetObject(Object retObject) {
        this.retObject = retObject;
    }
}
