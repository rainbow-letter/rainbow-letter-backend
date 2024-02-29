package com.handwoong.rainbowletter.data.infrastructure;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.data.domain.Data;

import lombok.Getter;

@Getter
@Entity
@Table(name = "data")
public class DataEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String event;

    private String device;

    private String deviceName;

    private String os;

    private String osName;

    private String osVersion;

    private String agent;

    private String agentName;

    private String agentVersion;

    public static DataEntity from(final Data data) {
        final DataEntity dataEntity = new DataEntity();
        dataEntity.event = data.event();
        dataEntity.device = data.device();
        dataEntity.deviceName = data.deviceName();
        dataEntity.os = data.os();
        dataEntity.osName = data.osName();
        dataEntity.osVersion = data.osVersion();
        dataEntity.agent = data.agent();
        dataEntity.agentName = data.agentName();
        dataEntity.agentVersion = data.agentVersion();
        return dataEntity;
    }

    public Data toModel() {
        return Data.builder()
                .id(id)
                .event(event)
                .device(device)
                .deviceName(deviceName)
                .os(os)
                .osName(osName)
                .osVersion(osVersion)
                .agent(agent)
                .agentName(agentName)
                .agentVersion(agentVersion)
                .build();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof final DataEntity that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
