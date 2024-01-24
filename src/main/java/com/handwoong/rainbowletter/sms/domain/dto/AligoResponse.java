package com.handwoong.rainbowletter.sms.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AligoResponse(int result_code, String message) {
}
