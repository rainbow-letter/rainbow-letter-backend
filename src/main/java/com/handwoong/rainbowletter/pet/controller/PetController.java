package com.handwoong.rainbowletter.pet.controller;

import com.handwoong.rainbowletter.common.util.SecurityUtils;
import com.handwoong.rainbowletter.pet.controller.response.PetResponse;
import com.handwoong.rainbowletter.pet.controller.response.PetResponses;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.domain.dto.PetCreate;
import com.handwoong.rainbowletter.pet.domain.dto.PetUpdate;
import com.handwoong.rainbowletter.pet.service.PetService;
import jakarta.validation.Valid;
import java.util.List;
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
        final List<Pet> pets = petService.findAll(email);
        final PetResponses response = PetResponses.from(pets);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> findOneById(@PathVariable final Long id) {
        final String email = SecurityUtils.getAuthenticationUsername();
        final Pet pet = petService.findById(email, id);
        final PetResponse response = PetResponse.from(pet);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final PetCreate request) {
        final String email = SecurityUtils.getAuthenticationUsername();
        petService.create(email, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable final Long id,
                                       @RequestBody @Valid final PetUpdate request) {
        final String email = SecurityUtils.getAuthenticationUsername();
        petService.update(email, id, request);
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
