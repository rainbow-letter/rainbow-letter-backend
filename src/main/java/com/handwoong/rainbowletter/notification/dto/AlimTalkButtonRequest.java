package com.handwoong.rainbowletter.notification.dto;

import java.util.List;

import com.handwoong.rainbowletter.notification.domain.TemplateButton;

public record AlimTalkButtonRequest(List<TemplateButton> button) {
}
