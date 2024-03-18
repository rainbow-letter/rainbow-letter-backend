package com.handwoong.rainbowletter.data.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.handwoong.rainbowletter.data.controller.port.DataService;
import com.handwoong.rainbowletter.data.domain.Data;
import com.handwoong.rainbowletter.data.domain.dto.DataCreate;
import com.handwoong.rainbowletter.data.service.port.DataRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataServiceImpl implements DataService {

    private final DataRepository dataRepository;

    @Override
    @Transactional
    public void create(final DataCreate dataCreate) {
        final Data data = Data.create(dataCreate);
        dataRepository.save(data);
    }
}
