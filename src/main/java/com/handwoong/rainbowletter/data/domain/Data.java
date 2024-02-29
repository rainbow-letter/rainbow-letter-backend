package com.handwoong.rainbowletter.data.domain;

import com.handwoong.rainbowletter.data.domain.dto.DataCreate;

import lombok.Builder;

@Builder
public record Data(
        Long id,
        String event,
        String device,
        String deviceName,
        String os,
        String osName,
        String osVersion,
        String agent,
        String agentName,
        String agentVersion
) {

    public static Data create(final DataCreate dataCreate) {
        return Data.builder()
                .event(dataCreate.event())
                .device(dataCreate.device())
                .deviceName(dataCreate.deviceName())
                .os(dataCreate.os())
                .osName(dataCreate.osName())
                .osVersion(dataCreate.osVersion())
                .agent(dataCreate.agent())
                .agentName(dataCreate.agentName())
                .agentVersion(dataCreate.agentVersion())
                .build();
    }
}
