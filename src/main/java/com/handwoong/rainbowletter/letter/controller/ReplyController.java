package com.handwoong.rainbowletter.letter.controller;

import com.handwoong.rainbowletter.letter.controller.port.ReplyService;
import com.handwoong.rainbowletter.letter.controller.request.ReplySubmitRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replies")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/admin/generate/{letterId}")
    public ResponseEntity<Void> generate(@PathVariable Long letterId) {
        replyService.generate(letterId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/admin/submit/{id}")
    public ResponseEntity<Void> submit(@PathVariable Long id,
                                       @RequestBody @Valid ReplySubmitRequest request) {
        replyService.submit(request.toDto(), id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
