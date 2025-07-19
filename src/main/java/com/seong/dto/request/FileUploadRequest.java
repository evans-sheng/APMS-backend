package com.seong.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * 文件上传请求DTO
 */
@Data
public class FileUploadRequest {

    /**
     * 上传的文件
     */
    @NotNull(message = "文件不能为空")
    private MultipartFile file;

    /**
     * 相册ID
     */
    private String albumId;
} 