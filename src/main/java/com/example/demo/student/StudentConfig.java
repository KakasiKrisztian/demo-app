package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
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

            repository.saveAll(
                    List.of(mihai,alex)
            );
        };
    }
}
