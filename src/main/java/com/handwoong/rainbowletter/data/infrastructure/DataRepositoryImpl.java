package com.handwoong.rainbowletter.data.infrastructure;

import org.springframework.stereotype.Repository;

import com.handwoong.rainbowletter.data.domain.Data;
import com.handwoong.rainbowletter.data.service.port.DataRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DataRepositoryImpl implements DataRepository {

    private final DataJpaRepository dataJpaRepository;

    @Override
    public Data save(final Data data) {
        return dataJpaRepository.save(DataEntity.from(data)).toModel();
    }
}
