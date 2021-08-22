package com.example.shirodemo.Common;

import lombok.Data;

/**
 * @author Nakano Miku
 */
@Data
public class Result {
    private int code;

    private String msg;

    private Object data;

    private static int SUCCESS = 200;

    private static int FAIL = 400;

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(String msg, Object data) {
        return new Result(SUCCESS, msg, data);
    }

    public static Result fail(String msg, Object data) {
        return new Result(FAIL, msg, data);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
