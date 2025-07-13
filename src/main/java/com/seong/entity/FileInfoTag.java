package com.seong.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文件标签关联实体类
 */
@Data
public class FileInfoTag {
    
    /**
     * 关联ID，UUID格式
     */
    private String id;
    
    /**
     * 文件ID
     */
    private String fileId;
    
    /**
     * 标签ID
     */
    private String tagId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 