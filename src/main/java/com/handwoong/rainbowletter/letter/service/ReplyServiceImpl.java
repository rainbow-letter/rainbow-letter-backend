package com.handwoong.rainbowletter.letter.service;

import com.handwoong.rainbowletter.letter.controller.port.ReplyService;
import com.handwoong.rainbowletter.letter.service.port.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
}
