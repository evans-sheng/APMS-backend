package com.seong.service;

import java.util.List;

/**
 * 收藏文件服务接口
 */
public interface FavoriteAlbumService {

    /**
     * 添加收藏
     */
    boolean addFavorite(String albumId);

    /**
     * 取消收藏
     */
    boolean removeFavorite(String albumId);

    List<String> queryAllFavorite();
}
