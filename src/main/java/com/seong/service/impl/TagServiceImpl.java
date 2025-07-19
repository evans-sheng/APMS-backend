package com.seong.service.impl;

import com.seong.common.PageResult;
import com.seong.common.exception.BusinessException;
import com.seong.dto.request.TagCreateRequest;
import com.seong.dto.request.TagUpdateRequest;
import com.seong.entity.Tag;
import com.seong.mapper.TagMapper;
import com.seong.service.TagService;
import com.seong.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标签服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    @Override
    @Transactional
    public Tag createTag(TagCreateRequest request) {
        // 检查标签名称是否已存在
        Tag existingTag = tagMapper.selectByName(request.getName());
        if (existingTag != null) {
            throw new BusinessException("标签名称已存在");
        }

        // 创建标签实体
        Tag tag = new Tag();
        tag.setId(UUIDUtils.generateUUID());
        tag.setName(request.getName());
        tag.setColor(StringUtils.hasText(request.getColor()) ? request.getColor() : "#FEF3C7");
        tag.setCreatedAt(LocalDateTime.now());
        tag.setUpdatedAt(LocalDateTime.now());

        // 保存到数据库
        int result = tagMapper.insertTag(tag);
        if (result <= 0) {
            throw new BusinessException("标签创建失败");
        }

        return tag;
    }

    @Override
    public PageResult<Tag> getTagList(Integer page, Integer limit, String search,
                                      String sortBy, String sortOrder) {
        // 计算偏移量
        int offset = (page - 1) * limit;

        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("search", search);
        params.put("sortBy", sortBy != null ? sortBy : "created_at");
        params.put("sortOrder", sortOrder != null ? sortOrder : "desc");
        params.put("offset", offset);
        params.put("limit", limit);

        // 查询数据
        List<Tag> tags = tagMapper.selectList(params);
        long total = tagMapper.selectCount(params);

        return PageResult.of(tags, total, page, limit);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagMapper.selectAll();
    }

    @Override
    public Tag getTagById(String tagId) {
        Tag tag = tagMapper.selectById(tagId);
        if (tag == null) {
            throw new BusinessException("标签不存在");
        }
        return tag;
    }

    @Override
    @Transactional
    public Tag updateTag(String tagId, TagUpdateRequest request) {
        // 检查标签是否存在
        Tag tag = tagMapper.selectById(tagId);
        if (tag == null) {
            throw new BusinessException("标签不存在");
        }

        // 检查名称是否重复（如果要更新名称）
        if (StringUtils.hasText(request.getName()) && !request.getName().equals(tag.getName())) {
            Tag existingTag = tagMapper.selectByName(request.getName());
            if (existingTag != null) {
                throw new BusinessException("标签名称已存在");
            }
            tag.setName(request.getName());
        }

        // 更新颜色
        if (StringUtils.hasText(request.getColor())) {
            tag.setColor(request.getColor());
        }

        tag.setUpdatedAt(LocalDateTime.now());

        // 更新数据库
        int result = tagMapper.updateTag(tag);
        if (result <= 0) {
            throw new BusinessException("标签更新失败");
        }

        return tag;
    }

    @Override
    @Transactional
    public boolean deleteTag(String tagId) {
        // 检查标签是否存在
        Tag tag = tagMapper.selectById(tagId);
        if (tag == null) {
            return false;
        }

        // 检查标签是否被相册使用
        int albumUsageCount = tagMapper.checkTagUsageInAlbums(tagId);
        if (albumUsageCount > 0) {
            throw new BusinessException("标签正在被相册使用，无法删除");
        }

        // 检查标签是否被文件使用
        int fileUsageCount = tagMapper.checkTagUsageInFiles(tagId);
        if (fileUsageCount > 0) {
            throw new BusinessException("标签正在被文件使用，无法删除");
        }

        // 删除标签
        int result = tagMapper.deleteTag(tagId);
        return result > 0;
    }
} 