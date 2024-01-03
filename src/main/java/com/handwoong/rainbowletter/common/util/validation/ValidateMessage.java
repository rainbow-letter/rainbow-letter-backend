package com.handwoong.rainbowletter.common.util.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateMessage {
    public static final String EMPTY_MESSAGE = "항목을 입력해주세요.";
    public static final String EMAIL_MESSAGE = "유효하지 않은 이메일 형식입니다.";
    public static final String PASSWORD_MESSAGE = "비밀번호는 영문, 숫자를 조합하여 8글자 이상으로 입력해주세요.";
    public static final String PHONE_NUMBER_MESSAGE = "유효하지 않은 휴대폰 번호 형식입니다.";
    public static final String LOGIN_MESSAGE = "이메일 및 비밀번호를 확인 해주세요.";
    public static final String FAQ_SUMMARY = "제목은 30자 이하로 입력해 주세요.";
    public static final String PET_NAME = "반려 동물의 이름은 20자 이하로 입력해 주세요.";
    public static final String PET_SPECIES = "반려 동물의 종류는 10자 이하로 입력해 주세요.";
    public static final String PET_OWNER = "주인을 부르는 호칭은 10자 이하로 입력해 주세요.";
    public static final String PET_DEATH_ANNIVERSARY = "날짜가 과거가 아니거나, 형식이 잘못되었습니다.";
    public static final String PET_PERSONALITY = "반려 동물의 성격은 10자 이하로 입력해 주세요.";
    public static final String PET_PERSONALITY_SIZE = "반려 동물의 성격은 최대 3개까지만 선택 가능합니다.";
    public static final String IMAGE_EMPTY = "이미지를 등록해주세요.";
}
