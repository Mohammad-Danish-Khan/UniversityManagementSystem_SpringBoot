package com.nisum.controller;

import com.nisum.model.Student;
import com.nisum.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/student")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> GetAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("{id}")
    public Student getStudent(@PathVariable Long id){
        return studentService.getStudent(id);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }

    @PostMapping
    public void addStudent(@RequestBody Student student) throws NoSuchFieldException {
        studentService.addStudent(student);
    }

    @PutMapping("{id}")
    public void updateStudent(@PathVariable Long id, @RequestBody Student student){
        studentService.updateStudent(id,student);
    }

}
