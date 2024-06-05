package com.handwoong.rainbowletter.notification.infrastructure;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handwoong.rainbowletter.common.config.notification.NotificationConfig;
import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import com.handwoong.rainbowletter.notification.application.port.MessageSender;
import com.handwoong.rainbowletter.notification.domain.Template;
import com.handwoong.rainbowletter.notification.dto.AlimTalkButtonRequest;
import com.handwoong.rainbowletter.notification.dto.AlimTalkRequest;
import com.handwoong.rainbowletter.notification.dto.ChangeStatusOrderResponse;
import com.handwoong.rainbowletter.notification.dto.ChangeStatusOrderResponse.LastChangeStatusOrderResponse;
import com.handwoong.rainbowletter.notification.dto.CommerceResponse;
import com.handwoong.rainbowletter.notification.dto.OrderDetailRequest;
import com.handwoong.rainbowletter.notification.dto.OrderDetailResponse;
import com.handwoong.rainbowletter.notification.dto.OrderDetailResponse.OrderDetailDataResponse;

import lombok.RequiredArgsConstructor;
import nl.basjes.parse.useragent.yauaa.shaded.org.apache.commons.lang3.StringUtils;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class NaverSmartStoreSender {
    private static final String COMMERCE_URL = "https://api.commerce.naver.com/external";

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final MessageSender messageSender;
    private final NotificationConfig notificationConfig;

    @Async
    @Scheduled(cron = "0 * * * * *")
    public void send() throws IOException {
        final String token = issueToken();
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", "Bearer " + token);
        final HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        final ZoneId zoneId = ZoneId.of("UTC");
        final String timestamp = ZonedDateTime.now(zoneId).withSecond(0).withNano(0).minusMinutes(1).format(formatter);

        final String res = restTemplate.exchange(
                        COMMERCE_URL + "/v1/pay-order/seller/product-orders/last-changed-statuses?lastChangedFrom=" + timestamp,
                        HttpMethod.GET, httpEntity, String.class)
                .getBody();
        final ChangeStatusOrderResponse response = mapper.readValue(res, ChangeStatusOrderResponse.class);

        if (Objects.isNull(response) || Objects.isNull(response.data())) {
            return;
        }

        final List<String> payed = new ArrayList<>();
        final List<String> delivered = new ArrayList<>();
        for (LastChangeStatusOrderResponse lastChangeResponse : response.data().lastChangeStatuses()) {
            if (lastChangeResponse.lastChangedType().equals("PAYED")) {
                payed.add(lastChangeResponse.productOrderId());
            }
            if (lastChangeResponse.lastChangedType().equals("DISPATCHED")) {
                delivered.add(lastChangeResponse.productOrderId());
            }
        }

        if (!payed.isEmpty()) {
            final HttpEntity<OrderDetailRequest> req1 = new HttpEntity<>(new OrderDetailRequest(payed), headers);
            final String res1 = restTemplate.exchange(
                            COMMERCE_URL + "/v1/pay-order/seller/product-orders/query", HttpMethod.POST, req1, String.class)
                    .getBody();
            final OrderDetailResponse detailRes = mapper.readValue(res1, OrderDetailResponse.class);
            if (Objects.nonNull(detailRes.data()) && !detailRes.data().isEmpty()) {
                for (OrderDetailDataResponse r : detailRes.data()) {
                    final AlimTalkRequest alimTalkRequest = AlimTalkRequest.builder()
                            .receiver(new PhoneNumber(r.order().ordererTel()))
                            .templateCode(Template.PAYED.getCode())
                            .subject(Template.PAYED.subject("아이"))
                            .failSubject(Template.PAYED.failSubject("아이"))
                            .content(Template.PAYED.content(
                                    r.productOrder().productName(),
                                    r.order().orderDate().substring(0, 16).replace("T", " "),
                                    r.productOrder().totalPaymentAmount()
                            ))
                            .failContent(Template.PAYED.failContent(
                                    r.productOrder().productName(),
                                    r.order().orderDate().substring(0, 16).replace("T", " "),
                                    r.productOrder().totalPaymentAmount()
                            ))
                            .buttons(new AlimTalkButtonRequest(Template.PAYED.buttons("")))
                            .useEmTitle(true)
                            .build();
                    messageSender.send(alimTalkRequest);
                }
            }
        }

        if (!delivered.isEmpty()) {
            final HttpEntity<OrderDetailRequest> req1 = new HttpEntity<>(new OrderDetailRequest(delivered), headers);
            final String res1 = restTemplate.exchange(
                            COMMERCE_URL + "/v1/pay-order/seller/product-orders/query", HttpMethod.POST, req1, String.class)
                    .getBody();
            final OrderDetailResponse detailRes = mapper.readValue(res1, OrderDetailResponse.class);
            if (Objects.nonNull(detailRes.data()) && !detailRes.data().isEmpty()) {
                for (OrderDetailDataResponse r : detailRes.data()) {
                    final AlimTalkRequest alimTalkRequest = AlimTalkRequest.builder()
                            .receiver(new PhoneNumber(r.order().ordererTel()))
                            .templateCode(Template.DELIVERY_START.getCode())
                            .subject(Template.DELIVERY_START.subject(""))
                            .failSubject(Template.DELIVERY_START.failSubject(""))
                            .content(Template.DELIVERY_START.content(
                                    "아이",
                                    r.productOrder().productName(),
                                    r.order().orderId(),
                                    r.productOrder().productName(),
                                    r.productOrder().shippingAddress().baseAddress(),
                                    r.delivery().trackingNumber()
                            ))
                            .failContent(Template.DELIVERY_START.failContent(
                                    "아이",
                                    r.productOrder().productName(),
                                    r.order().orderId(),
                                    r.productOrder().productName(),
                                    r.productOrder().shippingAddress().baseAddress(),
                                    r.delivery().trackingNumber()
                            ))
                            .buttons(new AlimTalkButtonRequest(Template.DELIVERY_START.buttons("")))
                            .useEmTitle(true)
                            .build();
                    messageSender.send(alimTalkRequest);
                }
            }
        }
    }

    private String issueToken() throws IOException {
        final long timestamp = System.currentTimeMillis();
        final String password = StringUtils.joinWith("_", notificationConfig.getCommerceId(), timestamp);
        final String hashedPw = BCrypt.hashpw(password, notificationConfig.getCommerceSecret());
        final String signature = Base64.getUrlEncoder().encodeToString(hashedPw.getBytes(StandardCharsets.UTF_8));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("client_id", notificationConfig.getCommerceId());
        body.add("timestamp", timestamp);
        body.add("grant_type", "client_credentials");
        body.add("client_secret_sign", signature);
        body.add("type", "SELF");

        final HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);
        final String responseBody = restTemplate.exchange(COMMERCE_URL + "/v1/oauth2/token", HttpMethod.POST,
                        httpEntity,
                        String.class)
                .getBody();
        return mapper.readValue(responseBody, CommerceResponse.class).access_token();
    }
}
