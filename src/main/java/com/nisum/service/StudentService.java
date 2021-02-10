package com.nisum.service;

import com.nisum.model.Student;
import com.nisum.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        studentRepository.save(student);
    }

    public void updateStudent(Long id, Student student){
        studentRepository.save(student);
    }
}
