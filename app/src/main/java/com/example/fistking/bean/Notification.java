package com.example.fistking.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Wachsbeere on 2018/9/20.
 */

public class Notification extends BmobObject {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
