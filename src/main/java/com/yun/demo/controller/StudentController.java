package com.yun.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yun.demo.Service.StudentService;
import com.yun.demo.bean.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @GetMapping("/findStudentList")
    public Map<String,Object> findStudentList(){
        Map<String,Object> map=new HashMap<>();
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        queryWrapper.isNull("Introduce_id");
        List<Student> list = studentService.list(queryWrapper);
        List<Student> students = loopStudent(list);
        map.put("1",students);
        return map;
    }

    public List<Student> getChildrenByPid(int pid){
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("Introduce_id",pid);
        List<Student> list = studentService.list(queryWrapper);
        return list;
    }

    public List<Student> loopStudent(List<Student> students){
        for(Student s:students){
            s.setChildren(this.getChildrenByPid(s.getId()));
            if(s.getChildren().isEmpty()){
                continue;
            }

            
            loopStudent(s.getChildren());
        }
        return students;
    }

}
