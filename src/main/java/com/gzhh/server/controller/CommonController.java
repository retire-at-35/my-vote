package com.gzhh.server.controller;

import com.gzhh.common.exception.BaseException;
import com.gzhh.common.result.Result;
import com.gzhh.common.util.AliOSSUtils;
import com.gzhh.server.service.CompetitorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class CommonController {
    @Autowired
    private AliOSSUtils aliOSSUtils;

    //文件上传
    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile img)
    {
        try {
            String url = aliOSSUtils.upload(img);
            return Result.success(url);
        } catch (IOException e) {
            throw new BaseException("上传失败");
        }
    }
}
