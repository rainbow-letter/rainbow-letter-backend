package com.handwoong.rainbowletter.letter.controller;

import com.handwoong.rainbowletter.letter.controller.port.LetterService;
import com.handwoong.rainbowletter.letter.dto.ReplyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
