package com.handwoong.rainbowletter.controller;

import static com.handwoong.rainbowletter.controller.member.MemberControllerTest.getToken;
import static com.handwoong.rainbowletter.util.Constants.ADMIN_EMAIL;
import static com.handwoong.rainbowletter.util.Constants.ADMIN_PASSWORD;
import static com.handwoong.rainbowletter.util.Constants.USER_EMAIL;
import static com.handwoong.rainbowletter.util.Constants.USER_PASSWORD;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import com.handwoong.rainbowletter.dto.member.MemberLoginRequest;
import com.handwoong.rainbowletter.util.DatabaseCleaner;

import io.restassured.RestAssured;

@Sql({"classpath:seed/data.sql"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerTestProvider {
    protected static final MemberLoginRequest userRequest = new MemberLoginRequest(USER_EMAIL, USER_PASSWORD);
    protected static final MemberLoginRequest adminRequest = new MemberLoginRequest(ADMIN_EMAIL, ADMIN_PASSWORD);

    protected static String userAccessToken;
    protected static String adminAccessToken;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        userAccessToken = getToken(userRequest);
        adminAccessToken = getToken(adminRequest);
    }

    @AfterEach
    void clear() {
        databaseCleaner.execute();
    }
}
