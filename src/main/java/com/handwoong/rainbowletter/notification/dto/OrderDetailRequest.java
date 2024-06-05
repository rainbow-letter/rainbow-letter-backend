package com.handwoong.rainbowletter.notification.dto;

import java.util.List;

public record OrderDetailRequest(List<String> productOrderIds) {
}
