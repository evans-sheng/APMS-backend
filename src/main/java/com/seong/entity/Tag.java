package com.seong.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 标签实体类
 */
@Data
public class Tag {
    
    /**
     * 标签ID，UUID格式
     */
    private String id;
    
    /**
     * 标签名称
     */
    private String name;
    
    /**
     * 标签颜色，十六进制
     */
    private String color;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 