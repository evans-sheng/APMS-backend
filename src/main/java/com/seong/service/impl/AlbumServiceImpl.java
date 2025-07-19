package com.seong.service.impl;

import com.seong.common.PageResult;
import com.seong.common.exception.BusinessException;
import com.seong.dto.request.AlbumCreateRequest;
import com.seong.dto.request.AlbumUpdateRequest;
import com.seong.entity.Album;
import com.seong.entity.AlbumTag;
import com.seong.entity.FileInfo;
import com.seong.entity.Tag;
import com.seong.mapper.AlbumMapper;
import com.seong.mapper.FileMapper;
import com.seong.mapper.TagMapper;
import com.seong.service.AlbumService;
import com.seong.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 相册服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    private final AlbumMapper albumMapper;
    private final FileMapper fileMapper;
    private final TagMapper tagMapper;

    @Override
    @Transactional
    public Album createAlbum(AlbumCreateRequest request) {
        // 创建相册实体
        Album album = new Album();
        album.setId(UUIDUtils.generateUUID());
        album.setName(request.getName());
        album.setDescription(request.getDescription());
        album.setPhotoCount(0);
        album.setCreatedAt(LocalDateTime.now());
        album.setUpdatedAt(LocalDateTime.now());
        album.setCoverPhotoId(request.getCoverPhotoId());
        // 保存到数据库
        int result = albumMapper.insert(album);
        if (result <= 0) {
            throw new BusinessException("相册创建失败");
        }

        // 处理标签
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            createAlbumTags(album.getId(), request.getTags());
        }

        return getAlbumById(album.getId());
    }

    @Override
    public PageResult<Album> getAlbumList(Integer page, Integer limit, String search, 
                                         String sortBy, String sortOrder) {
        // 计算偏移量
        int offset = (page - 1) * limit;

        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("search", search);
        params.put("sortBy", sortBy != null ? sortBy : "created_at");
        params.put("sortOrder", sortOrder != null ? sortOrder : "desc");
        params.put("offset", offset);
        params.put("limit", limit);

        // 查询数据
        List<Album> albums = albumMapper.selectList(params);
        long total = albumMapper.selectCount(params);

        // 设置标签信息
        albums.forEach(this::setAlbumTags);

        return PageResult.of(albums, total, page, limit);
    }

    @Override
    public Album getAlbumById(String albumId) {
        Album album = albumMapper.selectById(albumId);
        if (album == null) {
            throw new BusinessException("相册不存在");
        }

        // 设置标签信息
        setAlbumTags(album);

        return album;
    }

    @Override
    @Transactional
    public Album updateAlbum(String albumId, AlbumUpdateRequest request) {
        Album album = albumMapper.selectById(albumId);
        if (album == null) {
            throw new BusinessException("相册不存在");
        }

        // 更新基本信息
        if (request.getName() != null) {
            album.setName(request.getName());
        }
        if (request.getDescription() != null) {
            album.setDescription(request.getDescription());
        }
        if (request.getCoverPhotoId() != null) {
            // 验证封面照片是否存在且属于该相册
            FileInfo coverPhoto = fileMapper.selectById(request.getCoverPhotoId());
            if (coverPhoto == null) {
                throw new BusinessException("封面照片不存在");
            }
//            if (!albumId.equals(coverPhoto.getAlbumId())) {
//                throw new BusinessException("封面照片不属于该相册");
//            }
            album.setCoverPhotoId(request.getCoverPhotoId());
        }
        album.setUpdatedAt(LocalDateTime.now());

        // 更新相册信息
        int result = albumMapper.updateById(album);
        if (result <= 0) {
            throw new BusinessException("相册更新失败");
        }

        // 处理标签
        if (request.getTags() != null) {
            updateAlbumTags(albumId, request.getTags());
        }

        return getAlbumById(albumId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAlbum(String albumId) {
        Album album = albumMapper.selectById(albumId);
        if (album == null) {
            return false;
        }

        // 检查相册中是否有照片
        Map<String, Object> params = new HashMap<>();
        params.put("albumId", albumId);
        long photoCount = fileMapper.selectCount(params);
        if (photoCount > 0) {
            throw new BusinessException("相册中还有照片，无法删除");
        }

        // 软删除相册
        int result = albumMapper.deleteById(albumId);
        if (result <= 0) {
            return false;
        }

        // 删除标签关联
        tagMapper.deleteAlbumTagsByAlbumId(albumId);

        return true;
    }

    @Override
    public Map<String, Object> getAlbumPhotos(String albumId, Integer page, Integer limit, 
                                             String search, String sortBy, String sortOrder) {
        // 验证相册是否存在
        Album album = albumMapper.selectById(albumId);
        if (album == null) {
            throw new BusinessException("相册不存在");
        }

        // 计算偏移量
        int offset = (page - 1) * limit;

        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("albumId", albumId);
        params.put("search", search);
        params.put("sortBy", sortBy != null ? sortBy : "created_at");
        params.put("sortOrder", sortOrder != null ? sortOrder : "desc");
        params.put("offset", offset);
        params.put("limit", limit);

        // 查询照片数据
        List<FileInfo> photos = fileMapper.selectList(params);
        for (FileInfo fileInfo: photos) {
            List<String> tags = tagMapper.selectTagsByFileId(fileInfo.getId());
            fileInfo.setTags(tags);
        }
        long total = fileMapper.selectCount(params);

        // 设置文件URL
        photos.forEach(this::setFileUrls);

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("album", album);
        result.put("photos", PageResult.of(photos, total, page, limit));

        return result;
    }

    /**
     * 创建相册标签关联
     */
    private void createAlbumTags(String albumId, List<String> tagNames) {
        for (String tagName : tagNames) {
            Tag tag = tagMapper.selectByName(tagName);
            if (tag == null) {
                // 创建新标签
                tag = new Tag();
                tag.setId(UUIDUtils.generateUUID());
                tag.setName(tagName);
                tag.setColor("#" + UUIDUtils.generateShortUUID().substring(0, 6));
                tag.setCreatedAt(LocalDateTime.now());
                tag.setUpdatedAt(LocalDateTime.now());
                tagMapper.insertTag(tag);
            }

            // 创建关联
            AlbumTag albumTag = new AlbumTag();
            albumTag.setId(UUIDUtils.generateUUID());
            albumTag.setAlbumId(albumId);
            albumTag.setTagId(tag.getId());
            albumTag.setCreatedAt(LocalDateTime.now());
            tagMapper.insertAlbumTag(albumTag);
        }
    }

    /**
     * 更新相册标签
     */
    private void updateAlbumTags(String albumId, List<String> tagNames) {
        // 删除原有标签关联
        tagMapper.deleteAlbumTagsByAlbumId(albumId);

        // 添加新标签关联
        if (tagNames != null && !tagNames.isEmpty()) {
            createAlbumTags(albumId, tagNames);
        }
    }

    /**
     * 设置相册标签信息
     */
    private void setAlbumTags(Album album) {
        if (album == null) return;
        
        List<String> tags = tagMapper.selectTagsByAlbumId(album.getId());
        album.setTags(tags);
    }

    /**
     * 设置文件URL
     */
    private void setFileUrls(FileInfo fileInfo) {
        if (fileInfo == null) return;
        
        fileInfo.setUrl("/api/files/" + fileInfo.getId());
        if (fileInfo.getThumbnailPath() != null) {
//            fileInfo.setThumbnailUrl("/api/files/" + fileInfo.getId() + "?type=thumbnail");
            fileInfo.setThumbnailUrl("/api/files/" + fileInfo.getId());
        }
    }
} 