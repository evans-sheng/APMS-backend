package com.seong.service.impl;

import com.seong.common.PageResult;
import com.seong.common.exception.BusinessException;
import com.seong.config.FileConfig;
import com.seong.dto.request.FileUpdateRequest;
import com.seong.entity.FileInfo;
import com.seong.entity.FileInfoTag;
import com.seong.entity.Tag;
import com.seong.mapper.FileMapper;
import com.seong.mapper.TagMapper;
import com.seong.service.FileService;
import com.seong.utils.FileUtils;
import com.seong.utils.ThumbnailUtils;
import com.seong.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 文件服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final TagMapper tagMapper;
    private final FileConfig fileConfig;

    @Override
    @Transactional
    public FileInfo uploadFile(MultipartFile file, String albumId) {
        // 验证文件
        validateFile(file);

        try {
            // 生成文件信息
            String fileId = UUIDUtils.generateUUID();
            String originalName = file.getOriginalFilename();
            String extension = FileUtils.getFileExtension(originalName);
            String fileName = UUIDUtils.generateShortUUID() + "." + extension;
            
            // 生成存储路径
            String filePath = fileConfig.getPath() + fileName;
            String thumbnailPath = fileConfig.getThumbnailPath() + fileName;

            // 保存原图
            FileUtils.saveFile(file, filePath);

            // 生成缩略图
            try {
                ThumbnailUtils.generateThumbnail(filePath, thumbnailPath, fileConfig.getThumbnailSize());
            } catch (IOException e) {
                log.warn("生成缩略图失败: {}", e.getMessage());
                thumbnailPath = null;
            }

            // 创建文件实体
            FileInfo fileInfoEntity = new FileInfo();
            fileInfoEntity.setId(fileId);
            fileInfoEntity.setName(fileName);
            fileInfoEntity.setOriginalName(originalName);
            fileInfoEntity.setSize(file.getSize());
            fileInfoEntity.setType(FileUtils.getMimeType(originalName));
            fileInfoEntity.setExtension(extension);
            fileInfoEntity.setPath(filePath);
            fileInfoEntity.setThumbnailPath(thumbnailPath);
            if (albumId != null) {
                fileInfoEntity.setAlbumId(albumId);
            }
            fileInfoEntity.setCreatedAt(LocalDateTime.now());
            fileInfoEntity.setUpdatedAt(LocalDateTime.now());

            // 保存到数据库
            int result = fileMapper.insert(fileInfoEntity);
            if (result <= 0) {
                throw new BusinessException("文件保存失败");
            }

            // 更新相册照片数量
            if (albumId != null) {
                fileMapper.updatePhotoCountByAlbumId(albumId);
            }

            return fileInfoEntity;

        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Map<String, Object> uploadBatchFiles(MultipartFile[] files, String albumId) {
        Map<String, Object> result = new HashMap<>();
        List<FileInfo> successFileInfos = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                FileInfo uploadedFileInfo = uploadFile(file, albumId);
                successFileInfos.add(uploadedFileInfo);
            } catch (Exception e) {
                errorMessages.add(file.getOriginalFilename() + ": " + e.getMessage());
            }
        }

        result.put("successCount", successFileInfos.size());
        result.put("errorCount", errorMessages.size());
        result.put("files", successFileInfos);
        result.put("errors", errorMessages);

        return result;
    }

    @Override
    public PageResult<FileInfo> getFileList(Integer page, Integer limit, String albumId,
                                       String search, String sortBy, String sortOrder) {
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

        // 查询数据
        List<FileInfo> fileInfos = fileMapper.selectList(params);
        long total = fileMapper.selectCount(params);

        // 设置文件URL
        fileInfos.forEach(this::setFileUrls);

        return PageResult.of(fileInfos, total, page, limit);
    }

    @Override
    public FileInfo getFileById(String fileId) {
        FileInfo fileInfo = fileMapper.selectById(fileId);
        if (fileInfo == null) {
            throw new BusinessException("文件不存在");
        }

        // 设置文件URL
        setFileUrls(fileInfo);

        // 查询标签
        List<String> tags = tagMapper.selectTagsByFileId(fileId);
        fileInfo.setTags(tags);

        return fileInfo;
    }

    @Override
    @Transactional
    public FileInfo updateFile(String fileId, FileUpdateRequest request) {
        FileInfo fileInfo = fileMapper.selectById(fileId);
        if (fileInfo == null) {
            throw new BusinessException("文件不存在");
        }

        // 更新基本信息
        if (request.getName() != null) {
            fileInfo.setName(request.getName());
        }
        if (request.getAlbumId() != null) {
            fileInfo.setAlbumId(request.getAlbumId());
        }
        if (request.getDescription() != null) {
            fileInfo.setDescription(request.getDescription());
        }
        fileInfo.setUpdatedAt(LocalDateTime.now());

        // 更新文件信息
        int result = fileMapper.updateById(fileInfo);
        if (result <= 0) {
            throw new BusinessException("文件更新失败");
        }

        // 处理标签
        if (request.getTags() != null) {
            updateFileTags(fileId, request.getTags());
        }

        return getFileById(fileId);
    }

    @Override
    @Transactional
    public boolean deleteFile(String fileId) {
        FileInfo fileInfo = fileMapper.selectById(fileId);
        if (fileInfo == null) {
            return false;
        }

        // 软删除文件记录
        int result = fileMapper.deleteById(fileId);
        if (result <= 0) {
            return false;
        }

        // 删除物理文件
        try {
            FileUtils.deleteFile(fileInfo.getPath());
            if (fileInfo.getThumbnailPath() != null) {
                FileUtils.deleteFile(fileInfo.getThumbnailPath());
            }
        } catch (Exception e) {
            log.warn("删除物理文件失败: {}", e.getMessage());
        }

        // 删除标签关联
        tagMapper.deleteFileTagsByFileId(fileId);

        // 更新相册照片数量
        if (fileInfo.getAlbumId() != null) {
            fileMapper.updatePhotoCountByAlbumId(fileInfo.getAlbumId());
        }

        return true;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteBatchFiles(List<String> fileIds) {
        Map<String, Object> result = new HashMap<>();
        int successCount = 0;
        List<String> errorMessages = new ArrayList<>();

        for (String fileId : fileIds) {
            try {
                if (deleteFile(fileId)) {
                    successCount++;
                } else {
                    errorMessages.add("文件 " + fileId + " 删除失败");
                }
            } catch (Exception e) {
                errorMessages.add("文件 " + fileId + " 删除异常: " + e.getMessage());
            }
        }

        result.put("successCount", successCount);
        result.put("errorCount", errorMessages.size());
        result.put("errors", errorMessages);

        return result;
    }

    @Override
    public byte[] getFileContent(String fileId, String type) {
        FileInfo fileInfo = fileMapper.selectById(fileId);
        if (fileInfo == null) {
            throw new BusinessException("文件不存在");
        }

        try {
            String filePath;
            if ("thumbnail".equals(type) && fileInfo.getThumbnailPath() != null) {
                filePath = fileInfo.getThumbnailPath();
            } else {
                filePath = fileInfo.getPath();
            }

            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                throw new BusinessException("文件不存在");
            }

            return Files.readAllBytes(path);

        } catch (IOException e) {
            log.error("读取文件内容失败", e);
            throw new BusinessException("读取文件失败: " + e.getMessage());
        }
    }

    /**
     * 验证文件
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }

        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new BusinessException("文件名不能为空");
        }

        // 检查文件类型
        if (!FileUtils.isAllowedFileType(filename)) {
            throw new BusinessException("不支持的文件类型");
        }

        // 检查文件大小
        if (!FileUtils.isAllowedFileSize(file.getSize(), fileConfig.getMaxSize())) {
            throw new BusinessException("文件大小超过限制");
        }
    }

    /**
     * 设置文件URL
     */
    private void setFileUrls(FileInfo fileInfo) {
        if (fileInfo == null) return;
        
        // 这里可以根据实际需求设置文件访问URL
        fileInfo.setUrl("/api/files/" + fileInfo.getId());
        if (fileInfo.getThumbnailPath() != null) {
            fileInfo.setThumbnailUrl("/api/files/" + fileInfo.getId() + "?type=thumbnail");
        }
    }

    /**
     * 更新文件标签
     */
    private void updateFileTags(String fileId, List<String> tagNames) {
        // 删除原有标签关联
        tagMapper.deleteFileTagsByFileId(fileId);

        // 添加新标签关联
        if (tagNames != null && !tagNames.isEmpty()) {
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
                FileInfoTag fileInfoTag = new FileInfoTag();
                fileInfoTag.setId(UUIDUtils.generateUUID());
                fileInfoTag.setFileId(fileId);
                fileInfoTag.setTagId(tag.getId());
                fileInfoTag.setCreatedAt(LocalDateTime.now());
                tagMapper.insertFileTag(fileInfoTag);
            }
        }
    }
} 