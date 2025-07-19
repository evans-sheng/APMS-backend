package com.seong.service;

import java.util.List;

/**
 * 收藏文件服务接口
 */
public interface FavoriteFileService {
    
    /**
     * 添加收藏
     */
    boolean addFavorite(String fileId);
    
    /**
     * 取消收藏
     */
    boolean removeFavorite(String fileId);

    List<String> queryAllFavorite();
}
