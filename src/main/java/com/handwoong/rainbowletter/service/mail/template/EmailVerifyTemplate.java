package com.handwoong.rainbowletter.service.mail.template;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.handwoong.rainbowletter.config.PropertiesConfig;
import com.handwoong.rainbowletter.config.security.GrantType;
import com.handwoong.rainbowletter.config.security.JwtTokenProvider;
import com.handwoong.rainbowletter.config.security.TokenResponse;
import com.handwoong.rainbowletter.dto.mail.EmailTemplateDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailVerifyTemplate implements EmailTemplate {
    private final PropertiesConfig propertiesConfig;
    private final JwtTokenProvider tokenProvider;
    private final TemplateEngine templateEngine;

    @Override
    public EmailTemplateDto getTemplate(final String email) {
        final String subject = createSubject();
        final String body = createBody(email);
        return new EmailTemplateDto(subject, body);
    }

    private String createSubject() {
        return String.format(SUBJECT_FORMAT, "이메일 인증을 진행해주세요.");
    }

    private String createBody(final String email) {
        final TokenResponse tokenResponse =
                tokenProvider.generateToken(GrantType.PATH_VARIABLE, email, EmailTemplateType.VERIFY.name());
        final String verifyUrl = propertiesConfig.getClientUrls().get(0) + "/members/verify?token=" + tokenResponse.token();
        final Context context = new Context();
        context.setVariable("verifyUrl", verifyUrl);
        return templateEngine.process("verify", context);
    }
}
