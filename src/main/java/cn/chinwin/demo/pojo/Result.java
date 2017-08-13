package cn.chinwin.demo.pojo;

import java.io.Serializable;

/**
 * Created by chinwin on 2017/8/12.
 */
public class Result <T> implements Serializable{

    private Integer code;
    private String msg;
    private T result;

    public Result() {
    }

    public Result(Integer code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
