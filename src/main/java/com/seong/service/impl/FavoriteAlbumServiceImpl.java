package com.seong.service.impl;

import com.seong.entity.FavoriteAlbum;
import com.seong.entity.FavoriteFile;
import com.seong.mapper.FavoriteAlbumMapper;
import com.seong.mapper.FavoriteFileMapper;
import com.seong.service.FavoriteAlbumService;
import com.seong.service.FavoriteFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏文件服务实现类
 */
@Service
@RequiredArgsConstructor
public class FavoriteAlbumServiceImpl implements FavoriteAlbumService {

    private final FavoriteAlbumMapper favoriteAlbumMapper;

    /**
     * 添加收藏
     */
    @Override
    public boolean addFavorite(String albumId) {
        // 检查是否已经收藏
        FavoriteAlbum existing = favoriteAlbumMapper.selectByAlbumId(albumId);
        if (existing != null) {
            return false;
        }

        // 创建收藏记录
        FavoriteAlbum favoriteAlbum = new FavoriteAlbum();
        favoriteAlbum.setAlbumId(albumId);
        int result = favoriteAlbumMapper.insert(favoriteAlbum);
        return result > 0;
    }

    /**
     * 取消收藏
     */
    @Override
    public boolean removeFavorite(String albumId) {
        int result = favoriteAlbumMapper.deleteByAlbumId(albumId);
        return result > 0;
    }

    @Override
    public List<String> queryAllFavorite() {
        List<String> favoriteAlbums = favoriteAlbumMapper.selectAll();
        if (CollectionUtils.isEmpty(favoriteAlbums)) {
            return new ArrayList<>();
        }
        return favoriteAlbums;
    }
}
