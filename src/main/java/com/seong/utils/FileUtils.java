package com.seong.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * 文件操作工具类
 */
public class FileUtils {
    
    /**
     * 支持的图片格式
     */
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");
    
    /**
     * 获取文件扩展名
     */
    public static String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
    
    /**
     * 检查文件类型是否允许
     */
    public static boolean isAllowedFileType(String filename) {
        String extension = getFileExtension(filename);
        return ALLOWED_EXTENSIONS.contains(extension);
    }
    
    /**
     * 检查文件大小是否允许
     */
    public static boolean isAllowedFileSize(long fileSize, long maxSize) {
        return fileSize <= maxSize;
    }
    
    /**
     * 生成文件存储路径
     */
    public static String generateFilePath(String uploadPath, String filename) {
        String extension = getFileExtension(filename);
        String uuid = UUIDUtils.generateShortUUID();
        return uploadPath + File.separator + uuid + "." + extension;
    }
    
    /**
     * 保存文件
     */
    public static void saveFile(MultipartFile file, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        // 创建目录
        Files.createDirectories(path.getParent());
        // 保存文件
        file.transferTo(path.toFile());
    }
    
    /**
     * 删除文件
     */
    public static boolean deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * 检查目录是否存在，不存在则创建
     */
    public static void ensureDirectoryExists(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
    
    /**
     * 获取MIME类型
     */
    public static String getMimeType(String filename) {
        String extension = getFileExtension(filename);
        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "webp":
                return "image/webp";
            default:
                return "application/octet-stream";
        }
    }
} 