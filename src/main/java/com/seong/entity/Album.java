package com.seong.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 相册实体类
 */
@Data
public class Album {

    /**
     * 相册ID，UUID格式
     */
    private String id;

    /**
     * 相册名称
     */
    private String name;

    /**
     * 相册描述
     */
    private String description;

    /**
     * 封面照片ID
     */
    private String coverPhotoId;

    /**
     * 照片数量
     */
    private Integer photoCount;

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
     * 封面照片信息（关联查询时使用）
     */
    private FileInfo coverPhoto;

    /**
     * 是否被收藏
     */
    private Boolean isFavored;
} 