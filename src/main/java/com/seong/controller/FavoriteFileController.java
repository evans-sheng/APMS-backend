package com.seong.controller;

import com.seong.common.Result;
import com.seong.service.FavoriteFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * 收藏文件控制器
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Validated
public class FavoriteFileController {

    private final FavoriteFileService favoriteFileService;

    /**
     * 收藏照片
     */
    @PostMapping("/favorite/{fileId}")
    public Result<Boolean> addFavorite(@PathVariable @NotBlank String fileId) {
        boolean success = favoriteFileService.addFavorite(fileId);
        if (success) {
            return Result.success(true, "收藏成功");
        } else {
            return Result.error("收藏失败，可能已经收藏过了");
        }
    }

    /**
     * 取消收藏照片
     */
    @DeleteMapping("/favorite/{fileId}")
    public Result<Boolean> removeFavorite(@PathVariable @NotBlank String fileId) {
        boolean success = favoriteFileService.removeFavorite(fileId);
        if (success) {
            return Result.success(true, "取消收藏成功");
        } else {
            return Result.error("取消收藏失败，可能未收藏过该文件");
        }
    }
}
