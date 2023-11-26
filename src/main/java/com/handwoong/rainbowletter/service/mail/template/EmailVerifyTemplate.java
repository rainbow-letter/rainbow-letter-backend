package com.handwoong.rainbowletter.service.mail.template;

import org.springframework.stereotype.Component;

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
        final String verifyUrl = propertiesConfig.getClientUrls().get(0) + "/members/verify/" + tokenResponse.token();
        return """
                 <div style="font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;">
                 	<h1 style="margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;">
                 		<span style="color: #02b875;">메일인증</span> 안내입니다.
                 	</h1>
                 	<p style="font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;">
                 		아래 <b style="color: #02b875;">'메일 인증'</b> 버튼을 클릭하여 인증을 완료해 주세요.<br />
                 		감사합니다.
                 	</p>
                 	<a style="color: #FFF; text-decoration: none; text-align: center;" href="%s" target="_blank"><p style="display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;">메일 인증</p></a>
                 </div>
                """.formatted(verifyUrl);
    }
}
