package com.seong.mapper;

import com.seong.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文件Mapper接口
 */
@Mapper
public interface FileMapper {

    /**
     * 插入文件
     */
    int insert(FileInfo fileInfo);

    /**
     * 根据ID查询文件
     */
    FileInfo selectById(@Param("id") String id);

    /**
     * 查询文件列表
     */
    List<FileInfo> selectList(@Param("params") Map<String, Object> params);

    /**
     * 查询文件总数
     */
    long selectCount(@Param("params") Map<String, Object> params);

    /**
     * 更新文件信息
     */
    int updateById(FileInfo fileInfo);

    /**
     * 软删除文件
     */
    int deleteById(@Param("id") String id);

    /**
     * 批量软删除文件
     */
    int deleteBatchByIds(@Param("ids") List<String> ids);

    /**
     * 根据相册ID查询文件列表
     */
    List<FileInfo> selectByAlbumId(@Param("albumId") String albumId, @Param("params") Map<String, Object> params);

    /**
     * 根据相册ID查询文件总数
     */
    long selectCountByAlbumId(@Param("albumId") String albumId, @Param("params") Map<String, Object> params);

    /**
     * 更新相册中的文件数量
     */
    int updatePhotoCountByAlbumId(@Param("albumId") String albumId);
} 