package com.gzhh.server.controller;

import com.gzhh.common.result.PageResult;
import com.gzhh.common.result.Result;
import com.gzhh.pojo.dto.CompetitorsDTO;
import com.gzhh.pojo.dto.PageDTO;
import com.gzhh.pojo.entity.Competitors;
import com.gzhh.pojo.vo.Top10VO;
import com.gzhh.server.service.CompetitorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/competitors")
@Slf4j
public class CompetitorsController {

    @Autowired
    private CompetitorsService competitorsService;

    //分页查询
    @GetMapping("/page")
    public Result pageQuery(PageDTO pageDTO){
        log.info("pageDTO为{}",pageDTO);
        PageResult pageResult= competitorsService.pageQuery(pageDTO);
        return Result.success(pageResult);
    }


    //投票接口
    //接收参数为参赛人id
    @PutMapping("/vote")
    public Result vote(Integer id){
        log.info("参赛人id为{}",id);
        competitorsService.vote(id);
        return Result.success();
    }

    //获取总页数
    @GetMapping("/count")
    public Result getCount(){
        return Result.success(competitorsService.count());
    }


    //获取排行榜前十
    //返回值为两个数组(名字和票数),从小到大排序
    @GetMapping("/topTen")
    public Result top10()
    {
        Top10VO top10VO=competitorsService.top10();
        return Result.success(top10VO);
    }

    //导出excel表格
    @GetMapping("/export")
    public Result export(HttpServletResponse response)
    {
        competitorsService.export(response);
        return Result.success();
    }

    //新增参赛人
    @PostMapping("/save")
    public Result save(@RequestBody CompetitorsDTO competitorsDTO)
    {
        log.info("新增参赛人{}",competitorsDTO);
        //这里偷懒,在表现层写代码
        Competitors competitors = new Competitors();
        competitors.setCreateTime(LocalDateTime.now());
        competitors.setNumber(0);
        BeanUtils.copyProperties(competitorsDTO,competitors);
        competitorsService.save(competitors);
        return Result.success();
    }
}
