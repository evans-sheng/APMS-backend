package com.seong.service;

import com.seong.common.PageResult;
import com.seong.dto.request.AlbumCreateRequest;
import com.seong.dto.request.AlbumUpdateRequest;
import com.seong.entity.Album;

import java.util.Map;

/**
 * 相册服务接口
 */
public interface AlbumService {
    
    /**
     * 创建相册
     */
    Album createAlbum(AlbumCreateRequest request);
    
    /**
     * 获取相册列表
     */
    PageResult<Album> getAlbumList(Integer page, Integer limit, String search, 
                                  String sortBy, String sortOrder);
    
    /**
     * 根据ID获取相册
     */
    Album getAlbumById(String albumId);
    
    /**
     * 更新相册信息
     */
    Album updateAlbum(String albumId, AlbumUpdateRequest request);
    
    /**
     * 删除相册
     */
    boolean deleteAlbum(String albumId);
    
    /**
     * 获取相册中的照片
     */
    Map<String, Object> getAlbumPhotos(String albumId, Integer page, Integer limit, 
                                      String search, String sortBy, String sortOrder);
} 