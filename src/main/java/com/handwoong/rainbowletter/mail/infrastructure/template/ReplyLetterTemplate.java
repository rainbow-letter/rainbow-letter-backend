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
    public MailTemplate getTemplate(final Email email, final String url) {
        final String subject = createSubject();
        final String body = createBody(url);
        return new MailTemplate(subject, body);
    }

    private String createSubject() {
        return String.format(SUBJECT_FORMAT, "답장이 도착했어요!");
    }

    private String createBody(final String url) {
        final String letterDetailUrl = clientConfig.getClientUrl() + url;
        final Context context = new Context();
        context.setVariable("letterDetailUrl", letterDetailUrl);
        return templateEngine.process("reply", context);
    }
}
