package com.zaaach.citypicker.model;

import java.io.Serializable;

/**
 * Created by TeeMo111 on 2017/11/16.
 */

public class BaseBean implements Serializable {

    private static final long serialVersionUID = -1232740819156866958L;
    private int status;
    private String msg;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
