package com.example.demo.mapper;

import com.example.demo.model.SearchHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface SearchHistoryMapper {
    int deleteByPrimaryKey(Long searchid);

    int insert(SearchHistory row);

    int insertSelective(SearchHistory row);

    SearchHistory selectByPrimaryKey(Long searchid);

    int updateByPrimaryKeySelective(SearchHistory row);

    int updateByPrimaryKey(SearchHistory row);
    SearchHistory selectByUserid(Long userId);
}