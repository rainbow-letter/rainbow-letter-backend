package com.handwoong.rainbowletter.mail.service.template;

import com.handwoong.rainbowletter.common.config.client.ClientConfig;
import com.handwoong.rainbowletter.common.util.jwt.GrantType;
import com.handwoong.rainbowletter.common.util.jwt.JwtTokenProvider;
import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.mail.dto.EmailTemplateDto;
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
    public EmailTemplateDto getTemplate(final String email) {
        final String subject = createSubject();
        final String body = createBody(email);
        return new EmailTemplateDto(subject, body);
    }

    private String createSubject() {
        return String.format(SUBJECT_FORMAT, "비밀번호 변경 안내드립니다.");
    }

    private String createBody(final String email) {
        final TokenResponse tokenResponse =
                tokenProvider.generateToken(GrantType.PATH_VARIABLE, email, EmailTemplateType.VERIFY.name());
        final String resetPasswordUrl =
                clientConfig.getClientUrl() + "/members/password/reset?token=" + tokenResponse.token();
        final Context context = new Context();
        context.setVariable("resetPasswordUrl", resetPasswordUrl);
        return templateEngine.process("resetPassword", context);
    }
}
