package com.tracker.exception;

import lombok.Getter;

/**
 * 业务异常类
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String message;

    /**
     * 构造业务异常（默认错误码500）
     * @param message 错误信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }

    /**
     * 构造业务异常（自定义错误码）
     * @param code 错误码
     * @param message 错误信息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造业务异常（带原因）
     * @param message 错误信息
     * @param cause 原始异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
        this.message = message;
    }
}
