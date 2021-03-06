package com.example.demo.student.persistance;

import com.example.demo.student.domain.EnrollList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollListRepository extends JpaRepository<EnrollList, Long> {
    Page<EnrollList> findAllByListName(String listName, Pageable pageable);

    EnrollList findByListName(String listName);

    void deleteAllByListName(String name);
}
