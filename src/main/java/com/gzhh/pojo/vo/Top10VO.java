package com.gzhh.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Top10VO {
    private static final long serialVersionUID = 1L;
    //姓名列表,以逗号分隔,["张三","李四"]
    private String nameList;
    //票数列表,以逗号分隔,["1","2"]
    private String numberList;
}
