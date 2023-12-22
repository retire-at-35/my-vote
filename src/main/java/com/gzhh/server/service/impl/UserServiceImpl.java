package com.gzhh.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzhh.common.context.BaseContext;
import com.gzhh.common.exception.BaseException;
import com.gzhh.common.result.PageResult;
import com.gzhh.pojo.dto.PageDTO;
import com.gzhh.pojo.dto.PasswordDTO;
import com.gzhh.pojo.dto.RegisterDTO;
import com.gzhh.pojo.entity.Competitors;
import com.gzhh.pojo.entity.User;
import com.gzhh.server.mapper.UserMapper;
import com.gzhh.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    //登录操作
    public User login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        //先通过用户名到数据库里面查询
        User u = userMapper.getByName(username);
        if(u==null)
        {
            throw new BaseException("该账号不存在");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(u.getPassword()))
        {
            throw new BaseException("密码错误");
        }
        System.out.println(u);
        //返回账号
        return u;

    }

    //修改密码
    public void updatePassword(PasswordDTO passwordDTO) {
        //先对老密码进行比对
        String old = passwordDTO.getOldPassword();
        old=DigestUtils.md5DigestAsHex(old.getBytes());
        Long userId = BaseContext.getCurrentId();
        String password = userMapper.checkPassword(old, userId);
        log.info("password为:{}",password);
        if(password==null||!old.equals(password)||password.equals(""))
        {
            throw new BaseException("密码错误");
        }
        String newPassword = DigestUtils.md5DigestAsHex(passwordDTO.getNewPassword().getBytes());
        userMapper.updatePassword(newPassword,userId);
    }

//    //注册新用户
//    /*
//    * 逻辑为判断是否存在账号被占用的情况*/
//    public void register(RegisterDTO registerDTO,HttpServletRequest request) {
//        log.info("session为{}:",request.getSession().hashCode());
//        String kaptchaCode = request.getSession().getAttribute("verifyCode")+"";
//        log.info("kaptchaCode为{}:",kaptchaCode);
//        String code = registerDTO.getCode();
//        if (!StringUtils.hasLength(kaptchaCode)||!code.toLowerCase().equals(kaptchaCode)){
//            throw new BaseException("验证码错误");
//        }
//        String userName = registerDTO.getUserName();
//        User user = userMapper.getByName(userName);
//        if(user!=null)
//        {
//            throw new BaseException("该账号已存在");
//        }
//        BeanUtils.copyProperties(registerDTO,user);
//        //都没有错误注册新用户
//        userMapper.save(user);
//
//    }


//    //返回验证码图片
//    public void getCode(HttpSession session, HttpServletResponse httpServletResponse) throws IOException {
//        httpServletResponse.setHeader("Cache-Control","no-store");
//        //允许跨域
//        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
//        httpServletResponse.setHeader("Pragma","no-cache");
//        httpServletResponse.setDateHeader("Expires",0);
//        Cookie cookie = new Cookie("cookie_name", "cookie_value");
//        cookie.setSecure(true);
//        httpServletResponse.addCookie(cookie);
////        httpServletResponse.setContentType("image/gif");
//
//        //生成验证码对象,三个参数分别是宽、高、位数
//        SpecCaptcha captcha = new SpecCaptcha(130, 48, 5);
//        //设置验证码的字符类型为数字和字母混合
//        captcha.setCharType(Captcha.TYPE_DEFAULT);
//        // 设置内置字体
//        captcha.setCharType(Captcha.FONT_1);
//        //验证码存入session
//        session.setAttribute("verifyCode",captcha.text().toLowerCase()+"");
//        log.info("session为{}",session.hashCode());
//        log.info("验证码为{}",captcha.text().toLowerCase());
//        //输出图片流
//        captcha.out(httpServletResponse.getOutputStream());
//    }

    //注册新用户(无验证码版)
    public void register(RegisterDTO registerDTO) {
        String userName = registerDTO.getUsername();
        User user = userMapper.getByName(userName);
        if(user!=null)
        {
            throw new BaseException("该账号已存在");
        }
        User u = new User();
        u.setUsername(registerDTO.getUsername());
        u.setPassword(DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes()));
        //都没有错误注册新用户
        userMapper.save(u);
    }


}
