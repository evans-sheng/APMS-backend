package com.seong.mapper;

import com.seong.entity.Tag;
import com.seong.entity.AlbumTag;
import com.seong.entity.FileInfoTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 标签Mapper接口
 */
@Mapper
public interface TagMapper {
    
    /**
     * 插入标签
     */
    int insertTag(Tag tag);
    
    /**
     * 根据名称查询标签
     */
    Tag selectByName(@Param("name") String name);
    
    /**
     * 根据ID查询标签
     */
    Tag selectById(@Param("id") String id);
    
    /**
     * 查询所有标签
     */
    List<Tag> selectAll();
    
    /**
     * 分页查询标签列表
     */
    List<Tag> selectList(@Param("params") Map<String, Object> params);
    
    /**
     * 查询标签总数
     */
    long selectCount(@Param("params") Map<String, Object> params);
    
    /**
     * 更新标签信息
     */
    int updateTag(Tag tag);
    
    /**
     * 删除标签
     */
    int deleteTag(@Param("id") String id);
    
    /**
     * 检查标签是否被相册使用
     */
    int checkTagUsageInAlbums(@Param("tagId") String tagId);
    
    /**
     * 检查标签是否被文件使用
     */
    int checkTagUsageInFiles(@Param("tagId") String tagId);
    
    /**
     * 插入相册标签关联
     */
    int insertAlbumTag(AlbumTag albumTag);
    
    /**
     * 删除相册标签关联
     */
    int deleteAlbumTagsByAlbumId(@Param("albumId") String albumId);
    
    /**
     * 根据相册ID查询标签
     */
    List<String> selectTagsByAlbumId(@Param("albumId") String albumId);
    
    /**
     * 插入文件标签关联
     */
    int insertFileTag(FileInfoTag fileInfoTag);
    
    /**
     * 删除文件标签关联
     */
    int deleteFileTagsByFileId(@Param("fileId") String fileId);
    
    /**
     * 根据文件ID查询标签
     */
    List<String> selectTagsByFileId(@Param("fileId") String fileId);
} 