package com.gzhh.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gzhh.common.result.PageResult;
import com.gzhh.pojo.dto.PageDTO;
import com.gzhh.pojo.dto.PasswordDTO;
import com.gzhh.pojo.dto.RegisterDTO;
import com.gzhh.pojo.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface UserService extends IService<User> {
    User login(User user);


    void updatePassword(PasswordDTO dto);

//    void register(RegisterDTO registerDTO,HttpServletRequest request);

   // void getCode(HttpSession session, HttpServletResponse httpServletResponse) throws IOException;

    void register(RegisterDTO registerDTO);
}
