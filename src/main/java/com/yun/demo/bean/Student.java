package com.yun.demo.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yun.demo.poi.ExcelAttribute;
import lombok.Data;

import java.util.List;

@Data
@TableName("student")
public class Student {

    @ExcelAttribute(sort = 1)
    @TableId(type=IdType.INPUT)
    private int id;

    @ExcelAttribute(sort = 2)
    @TableField("Introduce_id")
    private int introduceId;

    @ExcelAttribute(sort = 3)
    @TableField("name")
    private String name;

    @TableField(exist = false)
    private List<Student> children;

}
