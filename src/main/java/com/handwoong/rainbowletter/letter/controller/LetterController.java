package com.handwoong.rainbowletter.letter.controller;

import com.handwoong.rainbowletter.letter.controller.port.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/letters")
public class LetterController {
    private final LetterService letterService;
}
