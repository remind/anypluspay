package com.anypluspay.commons.response;

/**
 * 返回结果
 * @param <T>
 */
public class ResponseResult<T> {

    /**
     * 结果码
     */
    private  String code;

    /**
     * 结果消息
     */
    private  String message;

    /**
     * 数据
     */
    private  T data;

    public ResponseResult() {
    }

    public ResponseResult(String code, String message, T data) {
        this.code = code == null ? GlobalResultCode.FAIL.getCode() : code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    /**
     * 返回成功，不带数据
     * @return
     */
    public static ResponseResult<String> success() {
        return success("");
    }

    /**
     * 返回成功并带数据
     * @param data  数据
     * @return
     * @param <T>
     */
    public static <T> ResponseResult<T> success(T data) {
        return success(GlobalResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 构造成功结果
     * @param message   消息提示
     * @param data      返回数据
     * @return  结果
     * @param <T>   数据类型
     */
    public static <T> ResponseResult<T> success(String message, T data) {
        return new ResponseResult<T>(GlobalResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 构造失败结果，使用默认错误码和错误消息
     * @return
     */
    public static ResponseResult<String> fail() {
        return new ResponseResult<>(GlobalResultCode.FAIL.getCode(), GlobalResultCode.FAIL.getMessage(), null);
    }

    /**
     * 构造失败结果，使用默认错误码
     * @param message
     * @return
     */
    public static ResponseResult<String> fail(String message) {
        return new ResponseResult<>(GlobalResultCode.FAIL.getCode(), message, null);
    }

    /**
     * 构造失败结果，指定错误码
     * @param resultCode
     * @return
     */
    public static ResponseResult<String> fail(ResultCode resultCode) {
        return new ResponseResult<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    /**
     * 构造失败结果，指定错误码和返回数据
     * @param resultCode
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseResult<T> fail(ResultCode resultCode, T data) {
        return new ResponseResult<>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    /**
     * 构造失败结果，指定错误码和消息
     * @param code
     * @param message
     * @return
     * @param <T>
     */
    public static <T> ResponseResult<T> fail(String code, String message) {
        return new ResponseResult<>(code, message, null);
    }

    /**
     * 是否成功
     * @return
     */
    public boolean isSuccess() {
        return GlobalResultCode.SUCCESS.getCode().equals(code);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
