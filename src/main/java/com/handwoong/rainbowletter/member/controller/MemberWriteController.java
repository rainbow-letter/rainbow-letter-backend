package com.handwoong.rainbowletter.member.controller;

import com.handwoong.rainbowletter.common.util.SecurityUtils;
import com.handwoong.rainbowletter.member.controller.port.MemberService;
import com.handwoong.rainbowletter.member.controller.request.ChangePasswordRequest;
import com.handwoong.rainbowletter.member.controller.request.MemberRegisterRequest;
import com.handwoong.rainbowletter.member.controller.request.PhoneNumberUpdateRequest;
import com.handwoong.rainbowletter.member.controller.request.ResetPasswordRequest;
import com.handwoong.rainbowletter.member.controller.response.MemberRegisterResponse;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberWriteController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberRegisterResponse> register(@RequestBody @Valid final MemberRegisterRequest request) {
        final Member member = memberService.register(request.toDto());
        final MemberRegisterResponse response = MemberRegisterResponse.from(member);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid final ChangePasswordRequest request) {
        final Email email = SecurityUtils.getAuthenticationUsername();
        memberService.changePassword(email, request.toDto());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid final ResetPasswordRequest request) {
        final Email email = SecurityUtils.getAuthenticationUsername();
        memberService.resetPassword(email, request.toDto());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/phoneNumber")
    public ResponseEntity<Void> changePhoneNumber(@RequestBody @Valid final PhoneNumberUpdateRequest request) {
        final Email email = SecurityUtils.getAuthenticationUsername();
        memberService.updatePhoneNumber(email, request.toDto());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/phoneNumber")
    public ResponseEntity<Void> deletePhoneNumber() {
        final Email email = SecurityUtils.getAuthenticationUsername();
        memberService.deletePhoneNumber(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Void> leave() {
        final Email email = SecurityUtils.getAuthenticationUsername();
        memberService.delete(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
