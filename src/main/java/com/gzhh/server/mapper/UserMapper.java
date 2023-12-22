package com.gzhh.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.gzhh.pojo.dto.PageDTO;
import com.gzhh.pojo.dto.Top10DTO;
import com.gzhh.pojo.entity.Competitors;
import com.gzhh.pojo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from myvote.user where username=#{username}")
    User getByName(String username);


    @Update("update myvote.user set number=number-1 where id=#{id}")
    void vote(Long id);

    @Select("select password from myvote.user where id =#{userId}")
    String checkPassword(String old,@Param("userId") Long userId);

    @Update("update myvote.user set password=#{newPassword} where id=#{userId}")
    void updatePassword(@Param("newPassword") String newPassword, @Param("userId") Long userId);
    @Update("update myvote.user set number=3")
    void addNumber();

    @Insert("insert into myvote.user(username,password) values(#{username},#{password})")
    void save(User user);
}
