package com.example.demo.student.service;

import com.example.demo.student.domain.EnrollList;
import com.example.demo.student.domain.Student;
import com.example.demo.student.exception.CustomException;
import com.example.demo.student.persistance.EnrollListRepository;
import com.example.demo.student.transfer.AddProductToCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollListService {

    private final EnrollListRepository enrollListRepository;

    @Autowired
    public EnrollListService(EnrollListRepository enrollListRepository){
        this.enrollListRepository = enrollListRepository;
    }

//    Not sure how this is paging by default?
    public List<EnrollList> getAllEnrollLists() {
        List<EnrollList> enrollListsFromDb = enrollListRepository.findAll();

        if (enrollListsFromDb.isEmpty()){
            throw new CustomException("No enroll lists active in database");
        }
        return enrollListsFromDb;
    }

    public Page<EnrollList> getEnrollListsByName(String name, Pageable pageable) {
        Page<EnrollList> allByListName = enrollListRepository.findAllByListName(name, pageable);
        if (allByListName.isEmpty())
            throw new CustomException("Could not find any enroll list by the name " + name);

        return allByListName;
    }

    public EnrollList addNewEnrollList(String name, String description) {
        EnrollList enrollList = new EnrollList(name, description);
        return enrollListRepository.save(enrollList);
    }

    public void addProductToCart(AddProductToCartRequest request) {
        EnrollList enrollList = enrollListRepository.getById(request.getEnrollListId());

        Student student;

//        enrollList.addToEnrollList();

    }
}
