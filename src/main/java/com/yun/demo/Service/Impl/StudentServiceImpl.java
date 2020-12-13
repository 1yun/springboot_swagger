package com.yun.demo.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.demo.Mapper.StudentMapper;
import com.yun.demo.Service.StudentService;
import com.yun.demo.bean.Student;
import com.yun.demo.config.DataSource;
import com.yun.demo.config.DataSourceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    @DataSource
    public List<Student> getAll() {
        return this.list();
    }
}
