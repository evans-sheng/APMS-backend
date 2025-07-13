package com.seong.common;

import lombok.Data;
import java.util.List;

/**
 * 分页响应结果类
 * @param <T> 数据类型
 */
@Data
public class PageResult<T> {
    
    /**
     * 数据列表
     */
    private List<T> list;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 当前页
     */
    private Integer page;
    
    /**
     * 每页数量
     */
    private Integer limit;
    
    /**
     * 总页数
     */
    private Integer totalPages;
    
    public PageResult() {
    }
    
    public PageResult(List<T> list, Long total, Integer page, Integer limit) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.limit = limit;
        this.totalPages = (int) Math.ceil((double) total / limit);
    }
    
    /**
     * 创建分页结果
     */
    public static <T> PageResult<T> of(List<T> list, Long total, Integer page, Integer limit) {
        return new PageResult<>(list, total, page, limit);
    }
} 