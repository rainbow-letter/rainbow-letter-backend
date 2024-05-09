package com.handwoong.rainbowletter.temporary.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.handwoong.rainbowletter.common.util.SecurityUtils;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.temporary.controller.port.TemporaryService;
import com.handwoong.rainbowletter.temporary.controller.request.TemporaryCreateRequest;
import com.handwoong.rainbowletter.temporary.controller.request.TemporaryUpdateRequest;
import com.handwoong.rainbowletter.temporary.domain.Temporary;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/temporaries")
public class TemporaryController {

    private final TemporaryService temporaryService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody TemporaryCreateRequest request) {
        final Email email = SecurityUtils.getAuthenticationUsername();
        final String sessionId = temporaryService.create(email, request.toDto());
        return ResponseEntity.created(URI.create(sessionId)).body(sessionId);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("temporary") Long id) {
        final Email email = SecurityUtils.getAuthenticationUsername();
        temporaryService.delete(email, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody TemporaryUpdateRequest request) {
        final Email email = SecurityUtils.getAuthenticationUsername();
        temporaryService.update(email, request.toDto());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/session")
    public ResponseEntity<String> changeSession(@RequestParam("temporary") Long id) {
        final Email email = SecurityUtils.getAuthenticationUsername();
        final String sessionId = temporaryService.changeSession(email, id);
        return ResponseEntity.ok(sessionId);
    }

    @GetMapping
    public ResponseEntity<Temporary> findByPetId() {
        final Email email = SecurityUtils.getAuthenticationUsername();
        final Temporary temporary = temporaryService.findByEmail(email);
        return ResponseEntity.ok(temporary);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> exists() {
        final Email email = SecurityUtils.getAuthenticationUsername();
        final boolean exists = temporaryService.exists(email);
        return ResponseEntity.ok(exists);
    }
}
