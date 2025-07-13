package com.seong.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 文件实体类
 */
@Data
public class FileInfo {
    
    /**
     * 文件ID，UUID格式
     */
    private String id;
    
    /**
     * 文件名
     */
    private String name;
    
    /**
     * 原始文件名
     */
    private String originalName;
    
    /**
     * 文件大小（字节）
     */
    private Long size;
    
    /**
     * 文件MIME类型
     */
    private String type;
    
    /**
     * 文件扩展名
     */
    private String extension;
    
    /**
     * 文件存储路径
     */
    private String path;
    
    /**
     * 缩略图存储路径
     */
    private String thumbnailPath;
    
    /**
     * 所属相册ID
     */
    private String albumId;
    
    /**
     * 文件描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    /**
     * 软删除时间
     */
    private LocalDateTime deletedAt;
    
    /**
     * 标签列表（关联查询时使用）
     */
    private List<String> tags;
    
    /**
     * 文件URL（业务字段，不存储到数据库）
     */
    private String url;
    
    /**
     * 缩略图URL（业务字段，不存储到数据库）
     */
    private String thumbnailUrl;
} 