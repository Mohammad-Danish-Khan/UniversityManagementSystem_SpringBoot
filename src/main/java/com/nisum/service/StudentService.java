package com.nisum.service;

import com.nisum.annotation.MyCustomAnnotation;
import com.nisum.model.Student;
import com.nisum.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void addStudent(Student student) throws NoSuchFieldException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        updateAnnotation(username);
        student.setCreatedBy();
//        student.setCreatedBy(username);
//        student.setUpdatedBy(username);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student);
    }

    public void updateStudent(Long id, Student student){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = ((UserDetails)principal).getUsername();
//        student.setUpdatedBy(username);
        student.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student);
    }

    public void updateAnnotation(String username) throws NoSuchFieldException {
        Field field = Student.class.getField("createdBy");
        final MyCustomAnnotation fieldAnnotation = field.getAnnotation(MyCustomAnnotation.class);
        System.out.println("old FieldAnnotation = " + fieldAnnotation.user());
        changeAnnotationValue(fieldAnnotation, "user", username);
        System.out.println("modified FieldAnnotation = " + fieldAnnotation.user());
    }
    @SuppressWarnings("unchecked")
    public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue){
        Object handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        f.setAccessible(true);
        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) f.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }
        memberValues.put(key,newValue);
        return oldValue;
    }
}
