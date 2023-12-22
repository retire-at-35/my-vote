package com.gzhh.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordDTO {
    private String oldPassword;//用来比较的老密码
    private String newPassword;//新密码
}
