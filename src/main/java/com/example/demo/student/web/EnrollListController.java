package com.example.demo.student.web;

import com.example.demo.student.domain.EnrollList;
import com.example.demo.student.service.EnrollListService;
import com.example.demo.student.transfer.AddStudentToEnrollListRequest;
import com.example.demo.student.transfer.UpdateEnrollListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/EnrollList")
public class EnrollListController {

    private final EnrollListService enrollListService;

    @Autowired
    public EnrollListController(EnrollListService enrollListService) {
        this.enrollListService = enrollListService;
    }

    @GetMapping
    public ResponseEntity<List<EnrollList>> getEnrollLists(){
        return new ResponseEntity<>(enrollListService.getAllEnrollLists(), HttpStatus.OK);
    }

    @GetMapping(value = "/EnrollList", params = "name")
    public ResponseEntity<Page<EnrollList>> getEnrollListsByName(@RequestBody String name, Pageable pageable){
        return new ResponseEntity<>(enrollListService.getEnrollListsByName(name, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EnrollList> addNewEnrollList(@RequestParam String name,
                                                       @RequestParam String description){
        return new ResponseEntity<>(enrollListService.addNewEnrollList(name, description), HttpStatus.OK);
    }

    @PutMapping("/{enrollListId}")
    public ResponseEntity<EnrollList> updateEnrollList(@RequestBody UpdateEnrollListRequest request, Long enrollListId){
        EnrollList enrollListUpdated = enrollListService.updateEnrollList(request, enrollListId);
        return new ResponseEntity(enrollListUpdated, HttpStatus.OK);
    }

    @PutMapping(value = "/EnrollList/AddStudentToList")
    public ResponseEntity addStudentToEnrollList(@RequestBody AddStudentToEnrollListRequest request){
        enrollListService.addStudentToEnrollList(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity deleteAllEnrollLists(){
        enrollListService.deleteAllEnrollLists();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/EnrollList", params = "name")
    public ResponseEntity deleteEnrollListByName(@RequestParam String name){
        enrollListService.deleteEnrollListByName(name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
