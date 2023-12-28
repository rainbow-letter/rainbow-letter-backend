package com.handwoong.rainbowletter.faq.controller;

import com.handwoong.rainbowletter.faq.controller.response.FAQAdminResponses;
import com.handwoong.rainbowletter.faq.controller.response.FAQUserResponses;
import com.handwoong.rainbowletter.faq.domain.FAQ;
import com.handwoong.rainbowletter.faq.domain.dto.FAQChangeSort;
import com.handwoong.rainbowletter.faq.domain.dto.FAQCreate;
import com.handwoong.rainbowletter.faq.domain.dto.FAQUpdate;
import com.handwoong.rainbowletter.faq.service.FAQService;
import jakarta.validation.Valid;
import java.util.List;
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
    public ResponseEntity<FAQUserResponses> findAllFAQs() {
        final List<FAQ> faqs = faqService.findAllByUser();
        final FAQUserResponses response = FAQUserResponses.from(faqs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list/admin")
    public ResponseEntity<FAQAdminResponses> findAdminAllFAQs() {
        final List<FAQ> faqs = faqService.findAllByAdmin();
        final FAQAdminResponses response = FAQAdminResponses.from(faqs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final FAQCreate request) {
        faqService.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/visibility/{id}")
    public ResponseEntity<Void> changeVisibility(@PathVariable final Long id) {
        faqService.changeVisibility(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/sequence/{id}")
    public ResponseEntity<Void> changeSortIndex(@PathVariable final Long id,
                                                @RequestBody final FAQChangeSort request) {
        faqService.changeSortIndex(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> edit(@PathVariable final Long id, @RequestBody @Valid final FAQUpdate request) {
        faqService.update(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        faqService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
