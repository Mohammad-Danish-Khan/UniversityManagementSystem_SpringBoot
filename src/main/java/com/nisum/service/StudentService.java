package com.nisum.service;

import com.nisum.model.Student;
import com.nisum.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        studentRepository.findAll()
        .forEach(students::add);
        return students;
    }
    public  Student getStudent(Long id){
        return studentRepository.findById(id).get();
    }
    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }

    public void addStudent(Student student){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        student.setCreatedBy(username);
        student.setUpdatedBy(username);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student);
    }

    public void updateStudent(Long id, Student student){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        student.setUpdatedBy(username);
        student.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student);
    }
}
