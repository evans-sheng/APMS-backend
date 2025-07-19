package com.seong.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 标签创建请求DTO
 */
@Data
public class TagCreateRequest {
    
    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空")
    private String name;
    
    /**
     * 标签颜色，十六进制格式
     */
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "颜色格式不正确，请使用十六进制格式如#FEF3C7")
    private String color;
} 