package com.example.demo.student.service;

import com.example.demo.student.domain.EnrollList;
import com.example.demo.student.domain.Student;
import com.example.demo.student.exception.CustomException;
import com.example.demo.student.persistance.EnrollListRepository;
import com.example.demo.student.persistance.StudentRepository;
import com.example.demo.student.transfer.AddStudentToEnrollListRequest;
import com.example.demo.student.transfer.UpdateEnrollListRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollListService {

    private final EnrollListRepository enrollListRepository;

    private final StudentService studentService;

    @Autowired
    public EnrollListService(EnrollListRepository enrollListRepository, StudentRepository studentRepository, StudentService studentService){
        this.enrollListRepository = enrollListRepository;
        this.studentService = studentService;
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

    public void addStudentToEnrollList(AddStudentToEnrollListRequest request) {
        EnrollList enrollList = enrollListRepository.findById(request.getEnrollListId())
                .orElse(addNewEnrollList("Blank title - please update", "Blank description - please update"));

        Student student = studentService.getStudentById(request.getStudentId());

        enrollList.addToEnrollList(student);

    }

    public void deleteAllEnrollLists() {
        enrollListRepository.deleteAll();
    }

    public void deleteEnrollListByName(String name) {
        enrollListRepository.deleteAllByListName(name);
    }

    public EnrollList updateEnrollList(UpdateEnrollListRequest request, Long id) {
        EnrollList enrollList = enrollListRepository.findById(id).orElseThrow(() -> new CustomException("Enroll list with id " + id + " does not exist"));

//        LOGGER.info("Updating student {}: {}", id, request);


        BeanUtils.copyProperties(request, enrollList);

        return enrollListRepository.save(enrollList);
    }
}
