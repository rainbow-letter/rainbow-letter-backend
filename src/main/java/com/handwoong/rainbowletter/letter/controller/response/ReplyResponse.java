package com.handwoong.rainbowletter.letter.controller.response;

import com.handwoong.rainbowletter.letter.domain.ReplyReadStatus;
import com.handwoong.rainbowletter.letter.domain.ReplyType;

public record ReplyResponse(Long id, String summary, String content, ReplyReadStatus readStatus, ReplyType type) {
}
