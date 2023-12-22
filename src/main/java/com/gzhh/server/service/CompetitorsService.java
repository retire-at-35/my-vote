package com.gzhh.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gzhh.common.result.PageResult;
import com.gzhh.pojo.dto.PageDTO;
import com.gzhh.pojo.entity.Competitors;
import com.gzhh.pojo.vo.Top10VO;

import javax.servlet.http.HttpServletResponse;


public interface CompetitorsService extends IService<Competitors> {
    PageResult pageQuery(PageDTO pageDTO);

    void vote(Integer id);


    Top10VO top10();

    void export(HttpServletResponse response);
}
