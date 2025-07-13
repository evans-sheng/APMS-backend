package com.seong.controller;

import com.seong.common.PageResult;
import com.seong.common.Result;
import com.seong.dto.request.FileUpdateRequest;
import com.seong.entity.FileInfo;
import com.seong.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件管理控制器
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class FileInfoController {

    private final FileService fileService;

    /**
     * 获取文件列表
     */
    @GetMapping("/list")
    public Result<Map<String, Object>> getFileList(@RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "20") Integer limit, @RequestParam(required = false) String albumId,
        @RequestParam(required = false) String search, @RequestParam(required = false) String sortBy,
        @RequestParam(defaultValue = "desc") String sortOrder) {

        PageResult<FileInfo> pageResult = fileService.getFileList(page, limit, albumId, search, sortBy, sortOrder);
        Map<String, Object> data = new HashMap<>();
        data.put("files", pageResult.getList());
        data.put("total", pageResult.getTotal());
        data.put("page", pageResult.getPage());
        data.put("limit", pageResult.getLimit());
        return Result.success(data);
    }

    /**
     * 获取单个文件
     */
    @GetMapping("/query/{fileId}")
    public Result<FileInfo> getFile(@PathVariable String fileId) {
        FileInfo fileInfo = fileService.getFileById(fileId);
        return Result.success(fileInfo);
    }

    /**
     * 上传单个文件
     */
    @PostMapping("/upload")
    public Result<FileInfo> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(required = false) String albumId) {

        FileInfo uploadedFileInfo = fileService.uploadFile(file, albumId);
        return Result.success(uploadedFileInfo, "文件上传成功");
    }

    /**
     * 批量上传文件
     */
    @PostMapping("/upload/batch")
    public Result<Map<String, Object>> uploadBatchFiles(@RequestParam("files") MultipartFile[] files,
        @RequestParam(required = false) String albumId) {

        Map<String, Object> result = fileService.uploadBatchFiles(files, albumId);
        return Result.success(result, "批量上传完成");
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/delete/{fileId}")
    public Result<Map<String, Object>> deleteFile(@PathVariable String fileId) {
        boolean deleted = fileService.deleteFile(fileId);
        Map<String, Object> data = new HashMap<>();
        data.put("deleted", deleted);
        data.put("fileId", fileId);
        return Result.success(data, "文件删除成功");
    }

    /**
     * 批量删除文件
     */
    @DeleteMapping("/batch")
    public Result<Map<String, Object>> deleteBatchFiles(@RequestBody Map<String, List<String>> request) {
        List<String> fileIds = request.get("fileIds");
        Map<String, Object> result = fileService.deleteBatchFiles(fileIds);
        return Result.success(result, "批量删除成功");
    }

    /**
     * 更新文件信息
     */
    @PutMapping("/update/{fileId}")
    public Result<FileInfo> updateFile(@PathVariable String fileId, @Valid @RequestBody FileUpdateRequest request) {
        FileInfo updatedFileInfo = fileService.updateFile(fileId, request);
        return Result.success(updatedFileInfo, "文件信息更新成功");
    }

    /**
     * 获取文件缩略图
     */
    @GetMapping("/getFile/{fileId}")
    public ResponseEntity<byte[]> getFileContent(@PathVariable String fileId, @RequestParam(required = false) String type) {
        // 暂时禁用缩略图功能，前端保持不变
        type = null;
        byte[] content = fileService.getFileContent(fileId, type);
        FileInfo fileInfo = fileService.getFileById(fileId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileInfo.getType()));

        return ResponseEntity.ok().headers(headers).body(content);
    }
}