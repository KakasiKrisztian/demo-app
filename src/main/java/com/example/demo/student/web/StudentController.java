package com.example.demo.student.web;

import com.example.demo.student.domain.Student;
import com.example.demo.student.service.StudentService;
import com.example.demo.student.transfer.UpdateStudentRequest;
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

    @GetMapping("/{studentIdGet}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentIdGet){
        return new ResponseEntity<>(studentService.getStudentById(studentIdGet), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity deleteStudent(@PathVariable("studentId") Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity deleteStudents(){
        studentService.deleteStudents();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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
        Student student = studentService.updateStudentWithNameAndEmail(id2, name, email);
        System.out.println(name + email);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
