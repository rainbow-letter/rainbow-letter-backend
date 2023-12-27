package com.handwoong.rainbowletter.faq.controller;

import com.handwoong.rainbowletter.faq.controller.response.FAQAdminResponse;
import com.handwoong.rainbowletter.faq.domain.dto.FAQChangeSequenceRequest;
import com.handwoong.rainbowletter.faq.domain.dto.FAQRequest;
import com.handwoong.rainbowletter.faq.controller.response.FAQResponse;
import com.handwoong.rainbowletter.faq.service.FAQService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/list/admin")
    public ResponseEntity<FAQAdminResponse> findAdminAllFAQs() {
        final FAQAdminResponse response = faqService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final FAQRequest request) {
        faqService.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/visibility/{faqId}")
    public ResponseEntity<Void> changeVisibility(@PathVariable final Long faqId) {
        faqService.changeVisibility(faqId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/sequence/{faqId}")
    public ResponseEntity<Void> changeSequence(@PathVariable final Long faqId,
                                               @RequestBody final FAQChangeSequenceRequest request) {
        faqService.changeSequence(faqId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{faqId}")
    public ResponseEntity<Void> edit(@PathVariable final Long faqId, @RequestBody @Valid final FAQRequest request) {
        faqService.edit(faqId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{faqId}")
    public ResponseEntity<Void> delete(@PathVariable final Long faqId) {
        faqService.delete(faqId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
