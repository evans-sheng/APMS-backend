package com.seong.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 文件存储配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileConfig {

    /**
     * 上传路径
     */
    private String path;

    /**
     * 缩略图路径
     */
    private String thumbnailPath;

    /**
     * 允许的扩展名
     */
    private String allowedExtensions;

    /**
     * 最大文件大小
     */
    private Long maxSize;

    /**
     * 缩略图大小
     */
    private Integer thumbnailSize;

    /**
     * 初始化创建目录
     */
    @PostConstruct
    public void init() {
        try {
            // 创建上传目录
            Files.createDirectories(Paths.get(path));
            // 创建缩略图目录
            Files.createDirectories(Paths.get(thumbnailPath));
        } catch (IOException e) {
            throw new RuntimeException("创建上传目录失败", e);
        }
    }
} 