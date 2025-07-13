package com.seong.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {
    
    /**
     * 错误码
     */
    private String code;
    
    public BusinessException(String message) {
        super(message);
        this.code = "BUSINESS_ERROR";
    }
    
    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = "BUSINESS_ERROR";
    }
    
    public BusinessException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
} 