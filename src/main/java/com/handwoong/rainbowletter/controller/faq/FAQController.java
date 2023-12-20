package com.handwoong.rainbowletter.controller.faq;

import com.handwoong.rainbowletter.dto.faq.FAQResponse;
import com.handwoong.rainbowletter.service.faq.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/faqs")
public class FAQController {
    private final FAQService faqService;

    @GetMapping
    public ResponseEntity<FAQResponse> findAllFAQs() {
        final FAQResponse response = faqService.findAllVisibilityTrue();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
