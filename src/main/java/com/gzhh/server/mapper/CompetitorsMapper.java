package com.gzhh.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.gzhh.pojo.dto.PageDTO;
import com.gzhh.pojo.dto.Top10DTO;
import com.gzhh.pojo.entity.Competitors;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CompetitorsMapper extends BaseMapper<Competitors> {
    Page<Competitors> selectByPage(PageDTO pageDTO);

    @Update("update myvote.competitors set number=number+1 where id=#{id}")
    void vote(Integer id);

    @Select("select title,number from myvote.competitors order by number desc limit 10")
    List<Top10DTO> top10();

    @Select("select title,number from myvote.competitors order by number desc ")
    List<Top10DTO> getAll();
}
