package com.handwoong.rainbowletter.notification.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderDetailResponse(
        String traceId,
        List<OrderDetailDataResponse> data
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OrderDetailDataResponse(
            OrderDetailOrderResponse order,
            OrderDetailDeliveryResponse delivery,
            OrderDetailProductResponse productOrder
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OrderDetailOrderResponse(
            String orderDate,
            String ordererTel,
            String paymentDate,
            String orderId
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OrderDetailDeliveryResponse(
            boolean isWrongTrackingNumber,
            String trackingNumber,
            String sendDate
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OrderDetailProductResponse(
            String productName,
            int totalPaymentAmount,
            OrderDetailAddressResponse shippingAddress
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OrderDetailAddressResponse(
            String baseAddress
    ) {
    }
}
