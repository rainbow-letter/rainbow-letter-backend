package com.handwoong.rainbowletter.member.controller;

import com.handwoong.rainbowletter.common.util.SecurityUtils;
import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.member.controller.response.MemberInfoResponse;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.dto.FindPassword;
import com.handwoong.rainbowletter.member.domain.dto.MemberLogin;
import com.handwoong.rainbowletter.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberReadController {
    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponse> info() {
        final String email = SecurityUtils.getAuthenticationUsername();
        final Member member = memberService.info(email);
        final MemberInfoResponse response = MemberInfoResponse.from(member);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid final MemberLogin request) {
        final TokenResponse token = memberService.login(request);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verify() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/password/find")
    public ResponseEntity<Void> findPassword(@RequestBody @Valid final FindPassword request) {
        memberService.findPassword(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
