package com.yun.demo.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.demo.Mapper.StudentMapper;
import com.yun.demo.Service.StudentService;
import com.yun.demo.bean.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
