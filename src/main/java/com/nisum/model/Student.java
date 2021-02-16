package com.nisum.model;

import com.nisum.annotation.MyCustomAnnotation;
import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public  class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;
    private String name;
    private String address;
    private String dob;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @MyCustomAnnotation
    @Column(name = "created_by")
    public String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToMany
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Course> courses;


    public void setCreatedBy() throws NoSuchFieldException {
        Student student=new Student();
        Field field=student.getClass().getField("createdBy");
        MyCustomAnnotation myCustomAnnotation=field.getAnnotation(MyCustomAnnotation.class);
        this.createdBy=myCustomAnnotation.user();
    }
}
