package com.handwoong.rainbowletter.legacy;

import com.handwoong.rainbowletter.legacy.dto.ReplyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/legacy/letters")
public class LetterLegacyController {
    private final LetterLegacyService letterLegacyService;

    @PostMapping
    public ResponseEntity<Void> reply(@RequestBody final ReplyRequestDto replyRequest) {
        letterLegacyService.reply(replyRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
