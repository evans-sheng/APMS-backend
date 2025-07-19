package com.seong.controller;

import com.seong.common.PageResult;
import com.seong.common.Result;
import com.seong.dto.request.AlbumCreateRequest;
import com.seong.dto.request.AlbumUpdateRequest;
import com.seong.entity.Album;
import com.seong.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 相册管理控制器
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Validated
public class AlbumController {

    private final AlbumService albumService;

    /**
     * 创建相册
     */
    @PostMapping("/albums")
    public Result<Album> createAlbum(@Valid @RequestBody AlbumCreateRequest request) {
        Album album = albumService.createAlbum(request);
        return Result.success(album);
    }

    /**
     * 获取相册列表
     */
    @GetMapping("/albums")
    public Result<PageResult<Album>> getAlbumList(@RequestParam(defaultValue = "1") @Min(1) Integer page, @RequestParam(defaultValue = "20") @Min(1) Integer limit, @RequestParam(required = false) String search, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortOrder) {

        PageResult<Album> result = albumService.getAlbumList(page, limit, search, sortBy, sortOrder);
        return Result.success(result);
    }

    /**
     * 根据ID获取相册信息
     */
    @GetMapping("/albums/{albumId}")
    public Result<Album> getAlbumById(@PathVariable @NotBlank String albumId) {
        Album album = albumService.getAlbumById(albumId);
        return Result.success(album);
    }

    /**
     * 更新相册信息
     */
    @PutMapping("/albums/{albumId}")
    public Result<Album> updateAlbum(@PathVariable @NotBlank String albumId, @Valid @RequestBody AlbumUpdateRequest request) {

        Album album = albumService.updateAlbum(albumId, request);
        return Result.success(album);
    }

    /**
     * 删除相册
     */
    @DeleteMapping("/albums/{albumId}")
    public Result<Boolean> deleteAlbum(@PathVariable @NotBlank String albumId) {
        boolean success = albumService.deleteAlbum(albumId);
        return Result.success(success);
    }

    /**
     * 获取相册中的照片
     */
    @GetMapping("/albums/{albumId}/photos")
    public Result<Map<String, Object>> getAlbumPhotos(@PathVariable @NotBlank String albumId, @RequestParam(defaultValue = "1") @Min(1) Integer page, @RequestParam(defaultValue = "20") @Min(1) Integer limit, @RequestParam(required = false) String search, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortOrder) {
        Map<String, Object> result = albumService.getAlbumPhotos(albumId, page, limit, search, sortBy, sortOrder);
        return Result.success(result);
    }
} 