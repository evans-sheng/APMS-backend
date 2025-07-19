package com.seong.service;

import com.seong.common.PageResult;
import com.seong.dto.request.TagCreateRequest;
import com.seong.dto.request.TagUpdateRequest;
import com.seong.entity.Tag;

import java.util.List;

/**
 * 标签服务接口
 */
public interface TagService {

    /**
     * 创建标签
     */
    Tag createTag(TagCreateRequest request);

    /**
     * 获取标签列表（分页）
     */
    PageResult<Tag> getTagList(Integer page, Integer limit, String search,
                               String sortBy, String sortOrder);

    /**
     * 获取所有标签
     */
    List<Tag> getAllTags();

    /**
     * 根据ID获取标签
     */
    Tag getTagById(String tagId);

    /**
     * 更新标签信息
     */
    Tag updateTag(String tagId, TagUpdateRequest request);

    /**
     * 删除标签
     */
    boolean deleteTag(String tagId);
} 