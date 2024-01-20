package com.example.demo2.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Faisal AL Sofyani"),
            new Student(2,"Mohmmmed hamed"),
            new Student(3, "Marwan Mobarak"),
            new Student(4, "Mohanned Aomer")
    );
    @GetMapping(path = "/{Students}")
    public Student getStudent(@PathVariable("Students") Integer studentId){
        return STUDENTS.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Student" + studentId + "Dose not exists"));
    }
}
