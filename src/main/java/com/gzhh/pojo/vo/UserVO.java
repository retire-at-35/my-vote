package com.gzhh.pojo.vo;

import com.gzhh.pojo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO extends User {
    private static final long serialVersionUID = 1L;
    private String token;
}
