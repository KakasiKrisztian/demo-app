package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity<List<Student>> getStudents(){
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id){
        studentService.deleteStudent(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudentRequest(@PathVariable Long id, @RequestBody UpdateStudentRequest request){
        Student student = studentService.updateStudent(id, request);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping("/{id2}")
    public ResponseEntity<Student> updateStudentNameAndEmail(
            @PathVariable Long id2,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        Student student = studentService.updateStudent(id2, name, email);
        System.out.println(name + email);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
