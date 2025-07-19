package com.seong.dto.request;

import lombok.Data;

import java.util.List;

/**
 * 相册更新请求DTO
 */
@Data
public class AlbumUpdateRequest {

    /**
     * 相册名称
     */
    private String name;

    /**
     * 相册描述
     */
    private String description;

    /**
     * 标签数组
     */
    private List<String> tags;

    /**
     * 封面照片文件ID
     */
    private String coverPhotoId;
} 