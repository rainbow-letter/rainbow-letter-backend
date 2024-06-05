package com.handwoong.rainbowletter.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import com.handwoong.rainbowletter.notification.controller.port.NotificationService;
import com.handwoong.rainbowletter.notification.controller.request.PhotoNotificationRequest;
import com.handwoong.rainbowletter.notification.domain.Template;
import com.handwoong.rainbowletter.notification.dto.AlimTalkButtonRequest;
import com.handwoong.rainbowletter.notification.dto.AlimTalkRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/photo")
    public ResponseEntity<Void> send(@RequestBody PhotoNotificationRequest request) {
        final AlimTalkRequest alimTalkRequest = AlimTalkRequest.builder()
                .receiver(new PhoneNumber(request.receiver()))
                .templateCode(Template.PHOTO.getCode())
                .subject(Template.PHOTO.subject(request.petName()))
                .failSubject(Template.PHOTO.failSubject(request.petName()))
                .content(Template.PHOTO.content(request.petName(), request.date()))
                .failContent(Template.PHOTO.failContent(request.petName(), request.date()))
                .buttons(new AlimTalkButtonRequest(Template.PHOTO.buttons("")))
                .useEmTitle(true)
                .build();
        notificationService.send(alimTalkRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
