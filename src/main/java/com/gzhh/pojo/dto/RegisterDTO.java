package com.gzhh.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDTO {
    private String username;//账号
    private String password;//密码
    private String code;//验证码

}
