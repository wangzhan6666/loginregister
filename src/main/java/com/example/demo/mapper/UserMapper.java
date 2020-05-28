package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    //根据用户名查询用户
    @Select("select * from user where userName = #{userName}")
    public User getUserByName(String userName);

    //添加用户账号、密码(注册)
    @Insert("insert into user (userName,passwd,status,registTime)  values (#{userName}, #{passwd}, #{status}, #{registTime})")
    public int addUser(User user);

}
