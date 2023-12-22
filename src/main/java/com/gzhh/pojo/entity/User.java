package com.gzhh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;//用户名

    private String password;//密码

    private Integer number;//票数

    private LocalDateTime createTime;//用户创建时间

}
