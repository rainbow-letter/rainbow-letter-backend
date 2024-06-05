package com.handwoong.rainbowletter.notification.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChangeStatusOrderResponse(
        String traceId,
        DataResponse data
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DataResponse(
            int count,
            List<LastChangeStatusOrderResponse> lastChangeStatuses
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record LastChangeStatusOrderResponse(
            String orderId,
            String productOrderId,
            String lastChangedType
    ) {
    }
}