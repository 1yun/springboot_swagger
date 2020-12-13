package com.yun.demo.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.demo.bean.Student;

import java.util.List;

public interface StudentService extends IService<Student> {

    public List<Student> getAll();

}
