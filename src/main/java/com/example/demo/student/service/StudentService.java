package com.example.demo.student.service;

import com.example.demo.student.transfer.UpdateStudentRequest;
import com.example.demo.student.domain.Student;
import com.example.demo.student.exception.CustomException;
import com.example.demo.student.persistance.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        LOGGER.info("Retrieving all students from DB");
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        LOGGER.info("Retrieving student with ID {} from DB", id);
        Optional<Student> studentById = studentRepository.findById(id);
        if(studentById.isPresent()) return studentById.get(); else throw new CustomException("Student with Id " + id + " could not be found");
    }

    public Student addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }
        LOGGER.info("Saving new student: {}", student.toString());
        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(Long id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new CustomException("Student with id " + id + " does not exist"));

        LOGGER.info("Updating student {}: {}", id, request);

        if (studentRepository.findStudentByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("This email is already taken");
        }

        BeanUtils.copyProperties(request, student);

        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudentWithNameAndEmail(Long id, String name, String email) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new CustomException("Student with id " + id + " does not exist"));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(email);

        studentByEmail.ifPresent(student1 -> {
            if (studentByEmail.get().getId().equals(id)) {
                throw new CustomException("You already have this email set");
            } else {
                throw new CustomException("This email is already taken");
            }
        });

        if (email != null && email.length() > 0) {
            student.setEmail(email);
        }

        LOGGER.info("Updating student {} with new name {} and email {}", id, name, email);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalStateException("Student with id " + id + " does not exist");
        }
        LOGGER.info("Deleting student with id {}", id);
        studentRepository.deleteById(id);
    }

    public void deleteStudents() {
        if (studentRepository.findAll().size() > 0) {
            LOGGER.info("Deleting all students");
            studentRepository.deleteAll();
        }else{
            LOGGER.info("The students database is already empty");
        }
    }
}
