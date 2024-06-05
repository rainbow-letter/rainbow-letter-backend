package com.handwoong.rainbowletter.notification.domain;

import java.util.List;

public interface TemplateContent {
    String subject(String petName);

    String failSubject(String petName);

    String content(Object... args);

    String failContent(Object... args);

    List<TemplateButton> buttons(String link);
}
