package com.handwoong.rainbowletter.letter.controller;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.handwoong.rainbowletter.common.util.SecurityUtils;
import com.handwoong.rainbowletter.letter.controller.port.LetterService;
import com.handwoong.rainbowletter.letter.controller.request.LetterCreateRequest;
import com.handwoong.rainbowletter.letter.controller.request.ReplyTypeRequest;
import com.handwoong.rainbowletter.letter.controller.response.LetterAdminResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponses;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.member.domain.Email;

import lombok.RequiredArgsConstructor;

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

    @GetMapping("/admin/list")
    public ResponseEntity<Page<LetterAdminResponse>> findAdminAll(
            @RequestParam(value = "startDate") LocalDate startDate,
            @RequestParam(value = "endDate") LocalDate endDate,
            @RequestParam(value = "email") String email,
            @RequestParam @Valid @NotNull ReplyTypeRequest type,
            @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
        final Page<LetterAdminResponse> response =
                letterService.findAllAdminLetters(type, startDate, endDate, email, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LetterResponse> findOne(@PathVariable Long id) {
        final Email email = SecurityUtils.getAuthenticationUsername();
        final LetterResponse response = letterService.findLetterById(email, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/share/{shareLink}")
    public ResponseEntity<LetterResponse> findShare(@PathVariable String shareLink) {
        final LetterResponse response = letterService.findLetterByShareLink(shareLink);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestParam("pet") Long petId,
                                       @RequestBody @Valid LetterCreateRequest request) {
        letterService.create(petId, request.toDto());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
