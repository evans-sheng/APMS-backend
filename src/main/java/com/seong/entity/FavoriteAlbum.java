package com.seong.entity;

import lombok.Data;

/**
 * 收藏文件实体类
 */
@Data
public class FavoriteAlbum {

    /**
     * 文件ID
     */
    private String albumId;

    /**
     * 创建时间（时间戳）
     */
    private String createTime;
}
