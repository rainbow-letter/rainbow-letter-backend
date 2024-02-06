package com.handwoong.rainbowletter.mail.infrastructure.template;

import com.handwoong.rainbowletter.common.config.client.ClientConfig;
import com.handwoong.rainbowletter.mail.domain.dto.MailTemplate;
import com.handwoong.rainbowletter.member.domain.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class ReplyLetterTemplate implements EmailTemplate {
    private final ClientConfig clientConfig;
    private final TemplateEngine templateEngine;

    @Override
    public MailTemplate getTemplate(final Email email, String subject, final String url) {
        final String subjectFormatted = String.format(SUBJECT_FORMAT, subject);
        final String body = createBody(url);
        return new MailTemplate(subjectFormatted, body);
    }

    private String createBody(final String url) {
        final String letterDetailUrl = clientConfig.getClientUrl().get(0) + url;
        final Context context = new Context();
        context.setVariable("letterDetailUrl", letterDetailUrl);
        return templateEngine.process("reply", context);
    }
}
