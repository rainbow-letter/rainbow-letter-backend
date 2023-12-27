package com.handwoong.rainbowletter.domain.pet.controller;

import com.handwoong.rainbowletter.domain.pet.dto.PetRequest;
import com.handwoong.rainbowletter.domain.pet.dto.PetResponse;
import com.handwoong.rainbowletter.domain.pet.dto.PetResponses;
import com.handwoong.rainbowletter.domain.pet.service.PetService;
import com.handwoong.rainbowletter.util.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    @GetMapping
    public ResponseEntity<PetResponses> findAll() {
        final String email = SecurityUtils.getAuthenticationUsername();
        final PetResponses response = petService.findAll(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> findOneById(@PathVariable final Long id) {
        final String email = SecurityUtils.getAuthenticationUsername();
        final PetResponse response = petService.findById(email, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final PetRequest request) {
        final String email = SecurityUtils.getAuthenticationUsername();
        petService.create(email, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> edit(@PathVariable final Long id,
                                     @RequestBody @Valid final PetRequest request) {
        final String email = SecurityUtils.getAuthenticationUsername();
        petService.edit(email, id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<Void> deleteImage(@PathVariable final Long id) {
        final String email = SecurityUtils.getAuthenticationUsername();
        petService.deleteImage(email, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        final String email = SecurityUtils.getAuthenticationUsername();
        petService.delete(email, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
