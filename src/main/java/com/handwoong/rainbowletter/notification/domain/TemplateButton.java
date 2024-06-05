package com.handwoong.rainbowletter.notification.domain;

import lombok.Builder;

@Builder
public record TemplateButton(
        String linkType,
        String linkTypeName,
        String name,
        String linkMo,
        String linkPc,
        String schemeIos,
        String schemeAndroid
) {
    public static TemplateButton createWebLinkButton(final String name, final String link) {
        return TemplateButton.builder()
                .linkType("WL")
                .linkTypeName("웹링크")
                .name(name)
                .linkPc(link)
                .linkMo(link)
                .build();
    }

    public static TemplateButton createBotKeywordButton(final String name) {
        return TemplateButton.builder()
                .linkType("BK")
                .linkTypeName("봇키워드")
                .name(name)
                .build();
    }

    public static TemplateButton createDeliveryButton() {
        return TemplateButton.builder()
                .linkType("DS")
                .linkTypeName("배송조회")
                .name("배송조회")
                .build();
    }

    public static TemplateButton createMessageServeButton(final String name) {
        return TemplateButton.builder()
                .linkType("MD")
                .linkTypeName("메시지전달")
                .name(name)
                .build();
    }
}
