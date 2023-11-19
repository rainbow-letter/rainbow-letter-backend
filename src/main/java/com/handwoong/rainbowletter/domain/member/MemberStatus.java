package com.handwoong.rainbowletter.domain.member;

public enum MemberStatus {
    INACTIVE,
    ACTIVE,
    SLEEP,
    LOCK,
    LEAVE;

    public boolean match(final MemberStatus status) {
        return this == status;
    }
}
