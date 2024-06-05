package com.handwoong.rainbowletter.notification.domain;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Template implements TemplateContent {
    REPLY("TT_1758") {
        @Override
        public String subject(final String petName) {
            return "[무지개편지] 편지답장 도착 알림";
        }

        @Override
        public String failSubject(final String petName) {
            return "[무지개편지]";
        }

        @Override
        public String content(final Object... args) {
            final String template = """
                    [무지개편지] 편지답장 도착 알림
                    \s
                    안녕하세요,
                    %s %s님!
                    %s, %s에게 쓴 답장이 도착했어요! : )
                    \s
                    아래 '답장 보러 가기' 버튼을 누르시면 편지함으로 이동합니다.
                    \s
                    무지개편지, 잘 이용하고 계신가요?
                    서비스 만족도 평가를 남겨주시면 무지개편지가 발전하는데 큰 힘이 됩니다.
                    """;
            return String.format(template, args);
        }

        @Override
        public String failContent(final Object... args) {
            final String template = """
                    [무지개편지]
                    \s
                    %s, %s에게 쓴 답장이 도착했어요! : )
                    \s
                    편지함에서 확인해주세요!
                    """;
            return String.format(template, args);
        }

        @Override
        public List<TemplateButton> buttons(final String link) {
            final TemplateButton replyButton = TemplateButton.createWebLinkButton("답장 보러 가기", link);
            final TemplateButton svButton = TemplateButton.createWebLinkButton(
                    "만족도 조사 하러가기", "https://forms.gle/gCMBwqukEqjBnBq9A");
            final TemplateButton bkButton = TemplateButton.createBotKeywordButton("문의하기");
            return List.of(replyButton, svButton, bkButton);
        }
    },
    DELIVERY_START("TT_1759") {
        @Override
        public String subject(final String petName) {
            return "[무지개편지] 배송 출발 안내";
        }

        @Override
        public String failSubject(final String petName) {
            return "[무지개편지] 배송 출발 안내";
        }

        @Override
        public String content(final Object... args) {
            final String template = """
                    [무지개편지] 배송 출발 안내
                    \s
                    %s와의 추억이 담긴 %s이 출발했어요!
                    \s
                    아래 [배송조회] 를 누르시면 배송 위치를 확인하실 수 있어요.
                    \s
                    □ 주문번호 : %s
                    □ 상품명 : %s
                    □ 배송지 : %s
                    □ 택배사 : 한진택배
                    □ 운송장 번호 : %s
                    """;
            return String.format(template, args);
        }

        @Override
        public String failContent(final Object... args) {
            final String template = """
                    [무지개편지] 배송 출발 안내
                    \s
                    %s와의 추억이 담긴 %s이 출발했어요!
                    \s
                    □ 주문번호 : %s
                    □ 상품명 : %s
                    □ 배송지 : %s
                    □ 택배사 : 한진택배
                    □ 운송장 번호 : %s
                    """;
            return String.format(template, args);
        }

        @Override
        public List<TemplateButton> buttons(final String link) {
            final TemplateButton deliveryButton = TemplateButton.createDeliveryButton();
            return List.of(deliveryButton);
        }
    },
    PAYED("TT_1760") {
        @Override
        public String subject(final String petName) {
            final String template = """
                    %s의
                    사진&편지를 보내주세요
                    """;
            return String.format(template, petName);
        }

        @Override
        public String failSubject(final String petName) {
            return "[무지개편지] 결제 완료 안내";
        }

        @Override
        public String content(final Object... args) {
            final String template = """
                    [무지개편지] 결제 완료 안내
                    %s 결제가 완료되었습니다.
                    \s
                    아래 [사진&편지 제출하기]를 눌러,
                    엽서북에 들어갈 아이의 사진과 편지를 보내주세요!
                    \s
                    아이의 사진과 편지를 보내주셔야 최종 주문 완료됩니다.
                    \s
                    □ 주문일 : %s
                    □ 결제금액 : #{결제금액}원
                    """;
            return String.format(template, args);
        }

        @Override
        public String failContent(final Object... args) {
            final String template = """
                    [무지개편지] 결제 완료 안내
                    %s 결제가 완료되었습니다.
                    \s
                    아래 [사진&편지 제출하기]를 눌러,
                    엽서북에 들어갈 아이의 사진과 편지를 보내주세요!
                    \s
                    아이의 사진과 편지를 보내주셔야 최종 주문 완료됩니다.
                    \s
                    □ 주문일 : %s
                    □ 결제금액 : %s원
                    \s
                    사진&편지 제출하기 링크 :
                    https://naver.me/GzgPtvGW
                    """;
            return String.format(template, args);
        }

        @Override
        public List<TemplateButton> buttons(final String link) {
            final TemplateButton webLinkButton = TemplateButton.createWebLinkButton(
                    "사진&편지 제출하기", "https://naver.me/GzgPtvGW");
            final TemplateButton messageServeButton = TemplateButton.createMessageServeButton("상품 문의하기");
            return List.of(webLinkButton, messageServeButton);
        }
    },
    PHOTO("TT_1761") {
        @Override
        public String subject(final String petName) {
            final String template = """
                    %s의
                    사진&편지를 보내주세요
                    """;
            return String.format(template, petName);
        }

        @Override
        public String failSubject(final String petName) {
            return "[무지개편지] 엽서북 제작을 위한 사진&편지 제출 안내";
        }

        @Override
        public String content(final Object... args) {
            final String template = """
                    [무지개편지]
                    엽서북 제작을 위한 사진&편지 제출 안내
                    \s
                    %s의 사진과 편지가 도착하지 않아 엽서북을 만들지 못하고 있어요.
                    \s
                    %s까지 제출해주지 않으면 결제가 취소돼요.
                    \s
                    아래 [사진&편지 제출하기] 를 눌러 제출해주세요!
                    """;
            return String.format(template, args);
        }

        @Override
        public String failContent(final Object... args) {
            final String template = """
                    [무지개편지]
                    엽서북 제작을 위한 사진&편지 제출 안내
                    \s
                    %s의 사진과 편지가 도착하지 않아 엽서북을 만들지 못하고 있어요.
                    \s
                    %s까지 제출해주지 않으면 결제가 취소돼요.
                    \s
                    아래 [사진&편지 제출하기] 를 눌러 제출해주세요!
                    \s
                    사진&편지 제출하기 :
                    https://naver.me/GzgPtvGW
                    """;
            return String.format(template, args);
        }

        @Override
        public List<TemplateButton> buttons(final String link) {
            final TemplateButton webLinkButton = TemplateButton.createWebLinkButton(
                    "사진&편지 제출하기", "https://naver.me/GzgPtvGW");
            final TemplateButton messageServeButton = TemplateButton.createMessageServeButton("상품 문의하기");
            return List.of(webLinkButton, messageServeButton);
        }
    };

    private final String code;
}
