package com.handwoong.rainbowletter.controller.letter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.handwoong.rainbowletter.dto.letter.ReplyRequestDto;
import com.handwoong.rainbowletter.service.letter.LetterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/letters")
public class LetterController {
    private final LetterService letterService;

    @PostMapping
    public ResponseEntity<Void> reply(@RequestBody final ReplyRequestDto replyRequest) {
        letterService.reply(replyRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
