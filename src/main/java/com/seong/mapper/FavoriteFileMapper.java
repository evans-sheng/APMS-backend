package com.seong.mapper;

import com.seong.entity.FavoriteFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏文件Mapper接口
 */
@Mapper
public interface FavoriteFileMapper {
    
    /**
     * 插入收藏记录
     */
    int insert(FavoriteFile favoriteFile);
    
    /**
     * 根据文件ID删除收藏记录
     */
    int deleteByFileId(@Param("fileId") String fileId);
    
    /**
     * 根据文件ID查询收藏记录
     */
    FavoriteFile selectByFileId(@Param("fileId") String fileId);

    /**
     * 查询全部收藏记录
     */
    List<String> selectAll();
}
