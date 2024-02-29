package com.handwoong.rainbowletter.data.controller.request;

import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMPTY_MESSAGE;

import jakarta.validation.constraints.NotBlank;

import com.handwoong.rainbowletter.data.domain.dto.DataCreate;

import nl.basjes.parse.useragent.UserAgent;

public record DataCreateRequest(@NotBlank(message = EMPTY_MESSAGE) String event) {

    public DataCreate toDto(final UserAgent userAgent) {
        return DataCreate.builder()
                .event(event)
                .device(userAgent.getValue("DeviceClass"))
                .deviceName(userAgent.getValue("DeviceName"))
                .os(userAgent.getValue("OperatingSystemClass"))
                .osName(userAgent.getValue("OperatingSystemName"))
                .osVersion(userAgent.getValue("OperatingSystemVersion"))
                .agent(userAgent.getValue("AgentClass"))
                .agentName(userAgent.getValue("AgentName"))
                .agentVersion(userAgent.getValue("AgentVersion"))
                .build();
    }
}
