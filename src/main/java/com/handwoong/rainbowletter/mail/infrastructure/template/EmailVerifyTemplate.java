package com.handwoong.rainbowletter.mail.infrastructure.template;

import com.handwoong.rainbowletter.common.config.client.ClientConfig;
import com.handwoong.rainbowletter.common.util.jwt.GrantType;
import com.handwoong.rainbowletter.common.util.jwt.JwtTokenProvider;
import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.member.domain.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class EmailVerifyTemplate implements EmailTemplate {
    private final ClientConfig clientConfig;
    private final JwtTokenProvider tokenProvider;
    private final TemplateEngine templateEngine;

    @Override
    public MailTemplate getTemplate(final Email email) {
        final String subject = createSubject();
        final String body = createBody(email);
        return new MailTemplate(subject, body);
    }

    private String createSubject() {
        return String.format(SUBJECT_FORMAT, "이메일 인증을 진행해주세요.");
    }

    private String createBody(final Email email) {
        final TokenResponse tokenResponse =
                tokenProvider.generateToken(GrantType.PATH_VARIABLE, email.toString(), MailTemplateType.VERIFY.name());
        final String verifyUrl =
                clientConfig.getClientUrl() + "/members/verify?token=" + tokenResponse.token();
        final Context context = new Context();
        context.setVariable("verifyUrl", verifyUrl);
        return templateEngine.process("verify", context);
    }
}
