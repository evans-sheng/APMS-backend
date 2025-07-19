package com.seong.service;

import com.seong.common.PageResult;
import com.seong.dto.request.FileUpdateRequest;
import com.seong.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 文件服务接口
 */
public interface FileService {

    /**
     * 上传单个文件
     */
    FileInfo uploadFile(MultipartFile file, String albumId);

    /**
     * 批量上传文件
     */
    Map<String, Object> uploadBatchFiles(MultipartFile[] files, String albumId);

    /**
     * 获取文件列表
     */
    PageResult<FileInfo> getFileList(Integer page, Integer limit, String albumId,
                                     String search, String sortBy, String sortOrder);

    /**
     * 根据ID获取文件
     */
    FileInfo getFileById(String fileId);

    /**
     * 更新文件信息
     */
    FileInfo updateFile(String fileId, FileUpdateRequest request);

    /**
     * 删除文件
     */
    boolean deleteFile(String fileId);

    /**
     * 批量删除文件
     */
    Map<String, Object> deleteBatchFiles(List<String> fileIds);

    /**
     * 获取文件内容（原图或缩略图）
     */
    byte[] getFileContent(String fileId, String type);
} 