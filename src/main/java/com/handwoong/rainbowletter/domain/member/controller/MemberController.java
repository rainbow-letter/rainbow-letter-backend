package com.handwoong.rainbowletter.domain.member.controller;

import com.handwoong.rainbowletter.config.security.TokenResponse;
import com.handwoong.rainbowletter.domain.member.dto.ChangePasswordRequest;
import com.handwoong.rainbowletter.domain.member.dto.ChangePhoneNumberRequest;
import com.handwoong.rainbowletter.domain.member.dto.FindPasswordDto;
import com.handwoong.rainbowletter.domain.member.dto.MemberLoginRequest;
import com.handwoong.rainbowletter.domain.member.dto.MemberRegisterRequest;
import com.handwoong.rainbowletter.domain.member.dto.MemberRegisterResponse;
import com.handwoong.rainbowletter.domain.member.dto.MemberResponse;
import com.handwoong.rainbowletter.domain.member.service.MemberService;
import com.handwoong.rainbowletter.util.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<MemberResponse> info() {
        final String email = SecurityUtils.getAuthenticationUsername();
        final MemberResponse response = memberService.info(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MemberRegisterResponse> register(@RequestBody @Valid final MemberRegisterRequest request) {
        final MemberRegisterResponse response = memberService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verify() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid final MemberLoginRequest request) {
        final TokenResponse token = memberService.login(request);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/password/find")
    public ResponseEntity<Void> findPassword(@RequestBody @Valid final FindPasswordDto request) {
        memberService.findPassword(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid final ChangePasswordRequest request) {
        final String email = SecurityUtils.getAuthenticationUsername();
        memberService.changePassword(email, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/phoneNumber")
    public ResponseEntity<Void> changePhoneNumber(@RequestBody @Valid final ChangePhoneNumberRequest request) {
        final String email = SecurityUtils.getAuthenticationUsername();
        memberService.changePhoneNumber(email, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/phoneNumber")
    public ResponseEntity<Void> deletePhoneNumber() {
        final String email = SecurityUtils.getAuthenticationUsername();
        memberService.deletePhoneNumber(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Void> deleteMember() {
        final String email = SecurityUtils.getAuthenticationUsername();
        memberService.delete(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
