package com.seong.controller;

import com.seong.common.PageResult;
import com.seong.common.Result;
import com.seong.dto.request.TagCreateRequest;
import com.seong.dto.request.TagUpdateRequest;
import com.seong.entity.Tag;
import com.seong.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 标签管理控制器
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/files/tags")
@RequiredArgsConstructor
@Validated
public class TagController {

    private final TagService tagService;

    /**
     * 创建标签
     */
    @PostMapping
    public Result<Tag> createTag(@Valid @RequestBody TagCreateRequest request) {
        Tag tag = tagService.createTag(request);
        return Result.success(tag, "标签创建成功");
    }

    /**
     * 获取标签列表（分页）
     */
    @GetMapping
    public Result<PageResult<Tag>> getTagList(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "20") @Min(1) Integer limit,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        
        PageResult<Tag> result = tagService.getTagList(page, limit, search, sortBy, sortOrder);
        return Result.success(result);
    }

    /**
     * 获取所有标签
     */
    @GetMapping("/all")
    public Result<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return Result.success(tags);
    }

    /**
     * 根据ID获取标签信息
     */
    @GetMapping("/{tagId}")
    public Result<Tag> getTagById(@PathVariable @NotBlank String tagId) {
        Tag tag = tagService.getTagById(tagId);
        return Result.success(tag);
    }

    /**
     * 更新标签信息
     */
    @PutMapping("/{tagId}")
    public Result<Tag> updateTag(
            @PathVariable @NotBlank String tagId,
            @Valid @RequestBody TagUpdateRequest request) {
        
        Tag tag = tagService.updateTag(tagId, request);
        return Result.success(tag, "标签更新成功");
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{tagId}")
    public Result<Boolean> deleteTag(@PathVariable @NotBlank String tagId) {
        boolean success = tagService.deleteTag(tagId);
        return Result.success(success, "标签删除成功");
    }
} 