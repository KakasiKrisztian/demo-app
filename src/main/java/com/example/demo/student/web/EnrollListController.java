package com.example.demo.student.web;

import com.example.demo.student.domain.EnrollList;
import com.example.demo.student.service.EnrollListService;
import com.example.demo.student.transfer.AddProductToCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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

    @PutMapping
    public ResponseEntity addProductToCart(@RequestBody AddProductToCartRequest request){
        enrollListService.addProductToCart(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

}
