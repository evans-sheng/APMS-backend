package com.seong.mapper;

import com.seong.entity.Album;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 相册Mapper接口
 */
@Mapper
public interface AlbumMapper {
    
    /**
     * 插入相册
     */
    int insert(Album album);
    
    /**
     * 根据ID查询相册
     */
    Album selectById(@Param("id") String id);
    
    /**
     * 查询相册列表
     */
    List<Album> selectList(@Param("params") Map<String, Object> params);
    
    /**
     * 查询相册总数
     */
    long selectCount(@Param("params") Map<String, Object> params);
    
    /**
     * 更新相册信息
     */
    int updateById(Album album);
    
    /**
     * 软删除相册
     */
    int deleteById(@Param("id") String id);
    
    /**
     * 更新相册照片数量
     */
    int updatePhotoCount(@Param("id") String id, @Param("photoCount") Integer photoCount);
}