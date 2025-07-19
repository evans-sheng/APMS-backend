package com.seong.entity;

import lombok.Data;

/**
 * 收藏文件实体类
 */
@Data
public class FavoriteFile {
    
    /**
     * 文件ID
     */
    private String fileId;
    
    /**
     * 创建时间（时间戳）
     */
    private Integer createtime;
}
