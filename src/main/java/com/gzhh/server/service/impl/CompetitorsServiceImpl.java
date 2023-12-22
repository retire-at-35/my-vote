package com.gzhh.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzhh.common.context.BaseContext;
import com.gzhh.common.exception.BaseException;
import com.gzhh.common.result.PageResult;
import com.gzhh.pojo.dto.PageDTO;
import com.gzhh.pojo.dto.Top10DTO;
import com.gzhh.pojo.entity.Competitors;
import com.gzhh.pojo.entity.User;
import com.gzhh.pojo.vo.Top10VO;
import com.gzhh.server.mapper.CompetitorsMapper;
import com.gzhh.server.mapper.UserMapper;
import com.gzhh.server.service.CompetitorsService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitorsServiceImpl extends ServiceImpl<CompetitorsMapper, Competitors> implements CompetitorsService {

    @Autowired
    private CompetitorsMapper competitorsMapper;

    @Autowired
    private UserMapper userMapper;

    //分页查询页面信息
    public PageResult pageQuery(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getPageSize());
        Page<Competitors> page = competitorsMapper.selectByPage(pageDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //投票接口 这里有个问题,当两用户同时点击的时候会不会出问题 todo
    @Transactional
    public void vote(Integer id) {
        User user = userMapper.selectById(BaseContext.getCurrentId());
        if(user.getNumber()<=0)
        {
            throw new BaseException("投票次数不足");
        }
        competitorsMapper.vote(id);
        userMapper.vote(BaseContext.getCurrentId());
    }

    //获取排行榜前十
    public Top10VO top10() {
        List<Top10DTO> top10List=competitorsMapper.top10();

        //通过流将里面的数据给提取出来接成一个字符串
        String titleList = top10List.stream()
                .map(Top10DTO::getTitle) // 获取所有name属性
                .collect(Collectors.joining(","));// 用逗号拼接成一个字符串

        //通过流将里面的数据给提取出来接成一个字符串
        String numberList = top10List.stream()
                .map(Top10DTO::getNumber) // 获取所有name属性
                .collect(Collectors.joining(","));// 用逗号拼接成一个字符串

        return new Top10VO(titleList,numberList);

    }

   //导出excel文件
    public void export(HttpServletResponse response) {
        //将所有的title还有number都给拿到,然后将他们写到excel里面
        List<Top10DTO> all = competitorsMapper.getAll();
        //通过流获得两个对应的集合
        List<String> titleList = all.stream()
                .map(Top10DTO::getTitle)
                .collect(Collectors.toList());

        List<String> numberList = all.stream()
                .map(Top10DTO::getNumber)
                .collect(Collectors.toList());
        //将模板excel加载到输入流
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("template\\排行榜.xlsx");

        try {
            XSSFWorkbook excel = new XSSFWorkbook(in);
            //初始化第一个excel表格
            XSSFSheet sheet1 = excel.getSheet("Sheet1");
            // 获取模板中的第一行
            XSSFRow templateRow = sheet1.getRow(0);

            // 获取姓名和票数的单元格索引
            int nameCellIndex = 0; // 姓名所在的列索引
            int numberCellIndex = 1; // 票数所在的列索引
            // 遍历titleList和numberList，并将数据写入到对应的单元格中
            for (int i = 0; i < titleList.size(); i++) {
                String title = titleList.get(i);
                String number = numberList.get(i);
                // 创建新的行
                XSSFRow newRow = sheet1.createRow(i + 1);
                // 在新行中创建单元格，并将数据写入
                XSSFCell nameCell = newRow.createCell(nameCellIndex);
                nameCell.setCellValue(title);
                XSSFCell numberCell = newRow.createCell(numberCellIndex);
                numberCell.setCellValue(number);
            }
            //通过输出流将excel文件下载到客户端
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);
            //释放资源
            out.close();
            excel.close();
        } catch (IOException e) {
            throw new BaseException(e.getMessage());
        }


    }

}
