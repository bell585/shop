package com.example.demo.mapper;

import com.example.demo.model.Users;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UsersMapper {
    int deleteByPrimaryKey(Long userid);

    int insert(Users row);

    int insertSelective(Users row);

    Users selectByPrimaryKey(Long userid);

    int updateByPrimaryKeySelective(Users row);

    int updateByPrimaryKey(Users row);
    Users selectAllByEmailUsers(String email);
}