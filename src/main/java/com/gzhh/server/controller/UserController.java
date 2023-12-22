package com.gzhh.server.controller;

import com.gzhh.common.context.BaseContext;
import com.gzhh.common.properties.JwtProperties;
import com.gzhh.common.result.PageResult;
import com.gzhh.common.result.Result;
import com.gzhh.common.util.JwtUtil;
import com.gzhh.pojo.dto.PageDTO;
import com.gzhh.pojo.dto.PasswordDTO;
import com.gzhh.pojo.dto.RegisterDTO;
import com.gzhh.pojo.entity.User;
import com.gzhh.pojo.vo.UserVO;
import com.gzhh.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;
    //注册新用户
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO registerDTO,HttpServletRequest request)
    {
        log.info("注册接口{}",registerDTO);
        userService.register(registerDTO);
        return Result.success();
    }



    //登录接口
    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody User user)
    {
        log.info("user为{}",user);
        User u = userService.login(user);
        //验证密码成功,发送jwt令牌;
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("userId",u.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims
        );
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(u,userVO);
        userVO.setToken(token);
        Result<UserVO> result = Result.success(userVO);
        result.setMsg("登录成功");
        return result;
    }

    //获取用户剩余票数
    @GetMapping("/getNum")
    public Result<Integer> getRemainNum()
    {
        User user = userService.getById(BaseContext.getCurrentId());
        return Result.success(user.getNumber());
    }

    //修改密码
    @PutMapping("/update")
    public Result update(@RequestBody PasswordDTO passwordDTO)
    {
        log.info("passwordDTO为{}",passwordDTO);
        userService.updatePassword(passwordDTO);
        return Result.success();
    }

//    //生成验证码
//    @GetMapping("/getCode")
//    public Result getCode(HttpSession session, HttpServletResponse httpServletResponse) throws IOException {
//        userService.getCode(session,httpServletResponse);
//        return Result.success();
//    }



}
