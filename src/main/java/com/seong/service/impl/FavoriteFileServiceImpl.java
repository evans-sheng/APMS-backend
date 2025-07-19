package com.seong.service.impl;

import com.seong.entity.FavoriteFile;
import com.seong.mapper.FavoriteFileMapper;
import com.seong.service.FavoriteFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 收藏文件服务实现类
 */
@Service
@RequiredArgsConstructor
public class FavoriteFileServiceImpl implements FavoriteFileService {
    
    private final FavoriteFileMapper favoriteFileMapper;
    
    /**
     * 添加收藏
     */
    @Override
    public boolean addFavorite(String fileId) {
        // 检查是否已经收藏
        FavoriteFile existing = favoriteFileMapper.selectByFileId(fileId);
        if (existing != null) {
            return false;
        }
        
        // 创建收藏记录
        FavoriteFile favoriteFile = new FavoriteFile();
        favoriteFile.setFileId(fileId);
        int result = favoriteFileMapper.insert(favoriteFile);
        return result > 0;
    }
    
    /**
     * 取消收藏
     */
    @Override
    public boolean removeFavorite(String fileId) {
        int result = favoriteFileMapper.deleteByFileId(fileId);
        return result > 0;
    }

    @Override
    public List<String> queryAllFavorite() {
        List<String> favoriteFiles = favoriteFileMapper.selectAll();
        if (CollectionUtils.isEmpty(favoriteFiles)) {
            return new ArrayList<>();
        }
        return favoriteFiles;
    }
}
