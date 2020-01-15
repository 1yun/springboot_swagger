package com.yun.demo.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.demo.Mapper.StudentMapper;
import com.yun.demo.Service.StudentService;
import com.yun.demo.bean.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
