package com.seong.controller;

import com.seong.common.Result;
import com.seong.service.FavoriteAlbumService;
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
public class FavoritAlbumController {

    private final FavoriteAlbumService favoriteAlbumService;

    /**
     * 收藏照片
     */
    @PostMapping("/album/favorite/{albumId}")
    public Result<Boolean> addFavorite(@PathVariable @NotBlank String albumId) {
        boolean success = favoriteAlbumService.addFavorite(albumId);
        if (success) {
            return Result.success(true, "收藏成功");
        } else {
            return Result.error("收藏失败，可能已经收藏过了");
        }
    }

    /**
     * 取消收藏照片
     */
    @DeleteMapping("/album/favorite/{albumId}")
    public Result<Boolean> removeFavorite(@PathVariable @NotBlank String albumId) {
        boolean success = favoriteAlbumService.removeFavorite(albumId);
        if (success) {
            return Result.success(true, "取消收藏成功");
        } else {
            return Result.error("取消收藏失败，可能未收藏过该文件");
        }
    }
}
