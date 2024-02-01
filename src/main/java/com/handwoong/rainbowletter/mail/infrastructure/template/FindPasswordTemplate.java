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
public class FindPasswordTemplate implements EmailTemplate {
    private final ClientConfig clientConfig;
    private final JwtTokenProvider tokenProvider;
    private final TemplateEngine templateEngine;

    @Override
    public MailTemplate getTemplate(final Email email, final String subject, final String url) {
        final String subjectFormatted = String.format(SUBJECT_FORMAT, subject);
        final String body = createBody(email, url);
        return new MailTemplate(subjectFormatted, body);
    }

    private String createBody(final Email email, final String url) {
        final TokenResponse tokenResponse =
                tokenProvider.generateToken(GrantType.PATH_VARIABLE, email.toString(), MailTemplateType.VERIFY.name());
        final String resetPasswordUrl =
                clientConfig.getClientUrl().get(0) + url + "?token=" + tokenResponse.token();
        final Context context = new Context();
        context.setVariable("resetPasswordUrl", resetPasswordUrl);
        return templateEngine.process("resetPassword", context);
    }
}
