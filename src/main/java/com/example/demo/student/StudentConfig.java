package com.example.demo.student;

import com.example.demo.student.domain.EnrollList;
import com.example.demo.student.domain.Student;
import com.example.demo.student.persistance.EnrollListRepository;
import com.example.demo.student.persistance.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository, EnrollListRepository enrollListRepository){
        return args -> {
            Student mihai =new Student(
                    1L,
                    "Mihai",
                    "mihai.mihai@yahoo.com",
                    LocalDate.of(2004, Month.FEBRUARY, 4)
            );
            Student alex =new Student(
                    "Alex",
                    "alex.alex@yahoo.com",
                    LocalDate.of(2003, Month.JANUARY, 1)
            );

            EnrollList enrollList = new EnrollList("Summer Camp 2022", "This is the enrolling list for this years Summer Camp!");

            enrollListRepository.save(enrollList);

            repository.saveAll(
                    List.of(mihai,alex)
            );
        };
    }
}
