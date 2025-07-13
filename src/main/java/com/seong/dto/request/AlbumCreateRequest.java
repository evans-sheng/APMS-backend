package com.seong.dto.request;

import lombok.Data;
import java.util.List;
import javax.validation.constraints.NotBlank;

/**
 * 相册创建请求DTO
 */
@Data
public class AlbumCreateRequest {
    
    /**
     * 相册名称
     */
    @NotBlank(message = "相册名称不能为空")
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