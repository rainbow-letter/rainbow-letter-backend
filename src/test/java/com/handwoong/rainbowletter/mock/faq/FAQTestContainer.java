package com.handwoong.rainbowletter.mock.faq;

import com.handwoong.rainbowletter.faq.controller.port.FAQService;
import com.handwoong.rainbowletter.faq.service.FAQServiceImpl;
import com.handwoong.rainbowletter.faq.service.port.FAQRepository;

public class FAQTestContainer {
    public final FAQRepository repository;
    public final FAQService service;

    public FAQTestContainer() {
        this.repository = new FakeFaqRepository();
        this.service = new FAQServiceImpl(repository);
    }
}
