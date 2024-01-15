package com.handwoong.rainbowletter.mail.domain.dto;

import com.handwoong.rainbowletter.member.domain.Email;

public record MailDto(Email email, String url) {
}
