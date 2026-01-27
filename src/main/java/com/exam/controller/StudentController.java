package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.StudentDTO;
import com.exam.service.StudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/student")
@CrossOrigin("*")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // ✅ ADD STUDENT
    @PostMapping("/add")
    public ResponseEntity<StudentDTO> addStudent(
            @Valid @RequestBody StudentDTO dto) {

        return ResponseEntity.ok(studentService.addStudent(dto));
    }

    // ✅ GET STUDENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {

        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // ✅ GET ALL STUDENTS
    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {

        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // ✅ DELETE STUDENT
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
