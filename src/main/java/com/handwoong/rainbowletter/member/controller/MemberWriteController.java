package com.handwoong.rainbowletter.member.controller;

import com.handwoong.rainbowletter.common.util.SecurityUtils;
import com.handwoong.rainbowletter.member.controller.response.MemberRegisterResponse;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.dto.ChangePassword;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.domain.dto.PhoneNumberUpdate;
import com.handwoong.rainbowletter.member.domain.dto.ResetPassword;
import com.handwoong.rainbowletter.member.service.MemberService;
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
    public ResponseEntity<MemberRegisterResponse> register(@RequestBody @Valid final MemberRegister request) {
        final Member member = memberService.register(request);
        final MemberRegisterResponse response = MemberRegisterResponse.from(member);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid final ChangePassword request) {
        final String email = SecurityUtils.getAuthenticationUsername();
        memberService.changePassword(email, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/password/reset")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid final ResetPassword request) {
        final String email = SecurityUtils.getAuthenticationUsername();
        memberService.resetPassword(email, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/phoneNumber")
    public ResponseEntity<Void> changePhoneNumber(@RequestBody @Valid final PhoneNumberUpdate request) {
        final String email = SecurityUtils.getAuthenticationUsername();
        memberService.updatePhoneNumber(email, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/phoneNumber")
    public ResponseEntity<Void> deletePhoneNumber() {
        final String email = SecurityUtils.getAuthenticationUsername();
        memberService.deletePhoneNumber(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Void> leave() {
        final String email = SecurityUtils.getAuthenticationUsername();
        memberService.delete(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
