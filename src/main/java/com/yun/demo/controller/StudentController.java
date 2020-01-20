package com.yun.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.deploy.net.HttpResponse;
import com.yun.demo.Service.StudentService;
import com.yun.demo.bean.Student;
import com.yun.demo.bean.User;
import com.yun.demo.utils.DownloadUtils;
import com.yun.demo.utils.ExcelImportUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/student")
public class StudentController {

    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;


    @RequestMapping(value="/admin/export",method=RequestMethod.GET)
    public void export(HttpServletResponse response, HttpServletRequest request)throws Exception{
        List<Student> list = studentService.list();

        //2.加载模板
      Resource resource = new ClassPathResource("excel-template/demo.xlsx");
        FileInputStream fis = new FileInputStream(resource.getFile());
        //3.根据模板创建工作簿
        Workbook wb = new XSSFWorkbook(fis);
        //4.读取工作表
        Sheet sheet = wb.getSheetAt(0);
        //5.抽取公共样式
        Row row = sheet.getRow(2);
        CellStyle styles [] = new CellStyle[row.getLastCellNum()];
        for(int i=0;i<row.getLastCellNum();i++) {
            Cell cell = row.getCell(i);
            styles[i] = cell.getCellStyle();
        }
        //6 构造单元格
        int rowIndex=2;
        Cell cell=null;
        for(Student s:list){
            System.out.println(s.toString());
            row=sheet.createRow(rowIndex++);

            cell=row.createCell(0);
            cell.setCellValue(s.getId());
            cell.setCellStyle(styles[0]);

            cell=row.createCell(1);
            cell.setCellValue(s.getIntroduceId());
            cell.setCellStyle(styles[1]);

            cell=row.createCell(2);
            cell.setCellValue(s.getName());
            cell.setCellStyle(styles[2]);

        }
        //3.完成下载
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        new DownloadUtils().download(os,response,"我的第二个.xlsx");

    }

    @PostMapping("/importUser")
    public void importUser(@RequestParam(name="file") MultipartFile file)throws Exception{
        List<Student> list = new ExcelImportUtil(Student.class).readExcel(file.getInputStream(), 2, 1);
        System.out.println(list.get(0).toString());
        System.out.println("正常----------------------");
    }




    @GetMapping("/findStudentList")
    public Map<String,Object> findStudentList(){
        if (LOG.isInfoEnabled()) {
            LOG.info("user log user findStudentList.");
        }
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
