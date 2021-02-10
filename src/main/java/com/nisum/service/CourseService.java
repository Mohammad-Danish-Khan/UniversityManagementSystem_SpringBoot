package com.nisum.service;

import com.nisum.model.Course;
import com.nisum.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getCourses(){
        List<Course> courses = new ArrayList<>();
        courseRepository.findAll()
                .forEach(courses::add);
        return courses;
    }
    public void addCourse(Course course){
        courseRepository.save(course);
    }
    public Course getCourseById(Long id){
        return courseRepository.findById(id).get();
    }

    public void updateCourse(Long id,Course course){
        courseRepository.save(course);
    }

    public void deleteCourse(Long id , Course course){
        courseRepository.delete(course);
    }

}
