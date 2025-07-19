package com.seong.common;

import lombok.Data;

/**
 * 统一响应结果类
 *
 * @param <T> 数据类型
 */
@Data
public class Result<T> {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String error;

    public Result() {
    }

    public Result(Boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public Result(Boolean success, String error, String code) {
        this.success = success;
        this.error = error;
        this.code = code;
    }

    /**
     * 成功响应
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(true, data, "操作成功");
    }

    /**
     * 成功响应，自定义消息
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(true, data, message);
    }

    /**
     * 失败响应
     */
    public static <T> Result<T> error(String error) {
        return new Result<>(false, error, "ERROR");
    }

    /**
     * 失败响应，自定义错误码
     */
    public static <T> Result<T> error(String error, String code) {
        return new Result<>(false, error, code);
    }
} 