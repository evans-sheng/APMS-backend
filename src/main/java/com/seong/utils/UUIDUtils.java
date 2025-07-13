package com.seong.utils;

import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtils {
    
    /**
     * 生成UUID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * 生成不带横线的UUID
     */
    public static String generateShortUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
} 