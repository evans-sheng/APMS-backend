package com.seong.dto.request;

import lombok.Data;

import java.util.List;

/**
 * 文件更新请求DTO
 */
@Data
public class FileUpdateRequest {

    /**
     * 文件名
     */
    private String name;

    /**
     * 相册ID
     */
    private String albumId;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 标签数组
     */
    private List<String> tags;
} 