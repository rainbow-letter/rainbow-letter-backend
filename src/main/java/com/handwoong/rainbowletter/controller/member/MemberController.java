package com.handwoong.rainbowletter.controller.member;

import com.handwoong.rainbowletter.config.security.TokenResponse;
import com.handwoong.rainbowletter.dto.member.ChangePasswordRequest;
import com.handwoong.rainbowletter.dto.member.FindPasswordDto;
import com.handwoong.rainbowletter.dto.member.MemberLoginRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterResponse;
import com.handwoong.rainbowletter.service.member.MemberService;
import com.handwoong.rainbowletter.util.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping
    public ResponseEntity<MemberRegisterResponse> register(@RequestBody @Valid final MemberRegisterRequest request) {
        final MemberRegisterResponse response = memberService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/verify/{token}")
    public ResponseEntity<Void> verify(@PathVariable final String token) {
        memberService.verify(token);
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
}
