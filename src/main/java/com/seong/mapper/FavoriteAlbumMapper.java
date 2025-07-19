package com.seong.mapper;

import com.seong.entity.FavoriteAlbum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏相册Mapper接口
 */
@Mapper
public interface FavoriteAlbumMapper {

    /**
     * 插入收藏记录
     */
    int insert(FavoriteAlbum favoriteAlbum);

    /**
     * 根据文件ID删除收藏记录
     */
    int deleteByAlbumId(@Param("albumId") String albumId);

    /**
     * 根据文件ID查询收藏记录
     */
    FavoriteAlbum selectByAlbumId(@Param("albumId") String albumId);

    /**
     * 查询全部收藏记录
     */
    List<String> selectAll();
}
