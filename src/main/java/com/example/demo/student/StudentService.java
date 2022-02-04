package com.example.demo.student;

import org.hibernate.internal.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalStateException("Student with id " + id + " does not exist");
        }
        studentRepository.deleteById(id);
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
    public Student updateStudent(Long id, String name, String email) {
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
//            if (studentByEmail.get().getId().equals(id)){
//                throw new CustomException("You already have this email set");
//            }else {
//                throw new CustomException("This email is already taken");
//            }


        if (email != null && email.length() > 0) {
            System.out.println("HERE HERE HEEEEEEERE " + studentRepository.findStudentByEmail(email).isPresent());
            student.setEmail(email);
        }

        System.out.println(student);
        return studentRepository.save(student);
    }
}
