package com.gzhh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Competitors implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String title;
    private Integer number;
    private LocalDateTime createTime;
    private String img;

}
