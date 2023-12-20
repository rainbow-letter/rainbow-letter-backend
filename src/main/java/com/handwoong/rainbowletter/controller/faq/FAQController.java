package com.handwoong.rainbowletter.controller.faq;

import com.handwoong.rainbowletter.dto.faq.FAQCreateRequest;
import com.handwoong.rainbowletter.dto.faq.FAQResponse;
import com.handwoong.rainbowletter.service.faq.FAQService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/faqs")
public class FAQController {
    private final FAQService faqService;

    @GetMapping("/list")
    public ResponseEntity<FAQResponse> findAllFAQs() {
        final FAQResponse response = faqService.findAllVisibilityTrue();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final FAQCreateRequest request) {
        faqService.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{faqId}")
    public ResponseEntity<Void> delete(@PathVariable final Long faqId) {
        faqService.delete(faqId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
