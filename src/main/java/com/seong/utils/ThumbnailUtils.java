package com.seong.utils;

import net.coobird.thumbnailator.Thumbnails;
import java.io.IOException;

/**
 * 缩略图生成工具类
 */
public class ThumbnailUtils {
    
    /**
     * 生成缩略图
     * @param sourceFile 源文件路径
     * @param targetFile 目标文件路径
     * @param size 缩略图大小
     */
    public static void generateThumbnail(String sourceFile, String targetFile, int size) throws IOException {
        Thumbnails.of(sourceFile)
                .size(size, size)
                .keepAspectRatio(true)
                .toFile(targetFile);
    }
    
    /**
     * 生成缩略图，默认200x200
     */
    public static void generateThumbnail(String sourceFile, String targetFile) throws IOException {
        generateThumbnail(sourceFile, targetFile, 200);
    }
} 