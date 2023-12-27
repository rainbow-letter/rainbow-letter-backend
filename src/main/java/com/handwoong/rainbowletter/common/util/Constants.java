package com.handwoong.rainbowletter.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    public static final long SECOND_TO_MILLISECOND = 1000L;
    public static final long MINUTE_TO_SECOND = 60L;
    public static final long HOUR_TO_MINUTE = 60L;
    public static final long DAY_TO_HOUR = 24L;
    public static final long WEEK_TO_DAY = 7L;
    public static final long TWO_WEEK_TO_MILLISECOND =
            SECOND_TO_MILLISECOND * MINUTE_TO_SECOND * HOUR_TO_MINUTE * DAY_TO_HOUR * (WEEK_TO_DAY + WEEK_TO_DAY);
    public static final long TEN_MINUTE_TO_MILLISECOND = SECOND_TO_MILLISECOND * MINUTE_TO_SECOND * 10L;
}
