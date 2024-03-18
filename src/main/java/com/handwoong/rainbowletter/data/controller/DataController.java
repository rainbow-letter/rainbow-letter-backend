package com.handwoong.rainbowletter.data.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.handwoong.rainbowletter.data.controller.port.DataService;
import com.handwoong.rainbowletter.data.controller.request.DataCreateRequest;

import lombok.RequiredArgsConstructor;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
public class DataController {

    private final UserAgentAnalyzer userAgentAnalyzer;
    private final DataService dataService;

    @PostMapping
    public ResponseEntity<Void> create(final HttpServletRequest request,
                                       @RequestBody @Valid final DataCreateRequest createRequest
    ) {
        final String userAgent = request.getHeader("USER-AGENT");
        final UserAgent agent = userAgentAnalyzer.parse(userAgent);
        dataService.create(createRequest.toDto(agent));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
