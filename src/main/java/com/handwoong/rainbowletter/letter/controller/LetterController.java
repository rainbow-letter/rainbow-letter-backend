package com.handwoong.rainbowletter.letter.controller;

import com.handwoong.rainbowletter.common.util.SecurityUtils;
import com.handwoong.rainbowletter.letter.controller.port.LetterService;
import com.handwoong.rainbowletter.letter.controller.request.LetterCreateRequest;
import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponses;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.member.domain.Email;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/letters")
public class LetterController {
    private final LetterService letterService;

    @GetMapping("/list")
    public ResponseEntity<LetterBoxResponses> findAll() {
        final Email email = SecurityUtils.getAuthenticationUsername();
        final List<LetterBoxResponse> letters = letterService.findAllLetterBoxByEmail(email);
        return ResponseEntity.ok(LetterBoxResponses.from(letters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LetterResponse> findOne(@PathVariable Long id) {
        final LetterResponse response = letterService.findLetterById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestParam("pet") Long petId,
                                       @RequestBody @Valid LetterCreateRequest request) {
        letterService.create(petId, request.toDto());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
