package com.nisum.controller;

import com.nisum.model.Course;
import com.nisum.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    public CourseService courseService;

    @GetMapping
    public List<Course> getAllCourse(){
        return courseService.getCourses();
    }

    @GetMapping("{id}")
    public Course  getCourse(@PathVariable Long id){
        return courseService.getCourseById(id);
    }

    @PostMapping
    public void addCourse(@RequestBody Course course){
        courseService.addCourse(course);
    }

    @PutMapping("{id}")
    public void updateCourse(@PathVariable Long id, @RequestBody Course course){
        courseService.updateCourse(id,course);
    }

    @DeleteMapping("{id}")
    public  void deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
    }

}
