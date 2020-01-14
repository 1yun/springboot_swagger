package com.yun.demo.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.demo.bean.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
