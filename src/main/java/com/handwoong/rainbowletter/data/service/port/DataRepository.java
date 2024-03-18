package com.handwoong.rainbowletter.data.service.port;

import com.handwoong.rainbowletter.data.domain.Data;

public interface DataRepository {
    Data save(Data data);
}
