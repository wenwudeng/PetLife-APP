package com.wenwu.pm.activity.review.bean;

import java.util.List;

/**
 * Created by moos on 2018/4/20.
 */

public class CommentBean {
    private String success;
    private String code;
    private String message;
    private Data data;

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public String getSuccess() { return success; }

    public void setSuccess(String success) { this.success = success; }

    public class Data {
        private int total;
        private List<CommentDetailBean> list;
        public void setTotal(int total) {
            this.total = total;
        }
        public int getTotal() {
            return total;
        }

        public void setList(List<CommentDetailBean> list) {
            this.list = list;
        }
        public List<CommentDetailBean> getList() {
            return list;
        }
    }



}