package com.handwoong.rainbowletter.letter.controller.request;

import com.handwoong.rainbowletter.letter.domain.ReplyType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReplyTypeRequest {
    ALL(null),
    WAIT(ReplyType.CHAT_GPT),
    COMPLETE(ReplyType.REPLY),
    ;

    private final ReplyType reply;
}
