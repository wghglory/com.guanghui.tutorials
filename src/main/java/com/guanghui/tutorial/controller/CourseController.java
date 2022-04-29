package com.guanghui.tutorial.controller;

import com.guanghui.tutorial.entity.Course;
import com.guanghui.tutorial.entity.CourseMaterial;
import com.guanghui.tutorial.model.CoursePayload;
import com.guanghui.tutorial.repository.CourseMaterialRepository;
import com.guanghui.tutorial.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseMaterialRepository courseMaterialRepository;

    @PostMapping
    public CourseMaterial createCourseAndCourseMaterial(@RequestBody CoursePayload coursePayload) {
        String title = coursePayload.getTitle();
        String url = coursePayload.getUrl();
        Integer credit = coursePayload.getCredit();

        Course course = Course.builder().title(title).credit(credit).build();
        CourseMaterial courseMaterial = CourseMaterial.builder().url(url).course(course).build();

        return courseMaterialRepository.save(courseMaterial);
    }

    @GetMapping()
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @GetMapping("/materials")
    public List<CourseMaterial> getAllMaterials() {
        return courseMaterialRepository.findAll();
    }

}
