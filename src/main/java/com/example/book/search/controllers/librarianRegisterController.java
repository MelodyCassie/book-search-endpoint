package com.example.book.search.controllers;

import com.example.book.search.dtos.request.LibrarianRegisterRequest;
import com.example.book.search.dtos.response.LibrarianRegisterResponse;
import com.example.book.search.services.LibrarianService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class librarianRegisterController {
    private final LibrarianService librarianService;

    @PostMapping("/liberian")
    public ResponseEntity<LibrarianRegisterResponse> register(@RequestBody LibrarianRegisterRequest registerRequest){
        return new ResponseEntity<>(librarianService.register(registerRequest), HttpStatus.CREATED);
    }
}
