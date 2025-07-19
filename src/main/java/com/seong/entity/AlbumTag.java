package com.seong.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 相册标签关联实体类
 */
@Data
public class AlbumTag {

    /**
     * 关联ID，UUID格式
     */
    private String id;

    /**
     * 相册ID
     */
    private String albumId;

    /**
     * 标签ID
     */
    private String tagId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 