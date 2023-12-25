package com.handwoong.rainbowletter.config;

import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PropertiesConfig {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("#{${client.url}}")
    private List<String> clientUrls;

    @Value("${chatgpt.token}")
    private String chatgptToken;

    @Value("${airtable.token}")
    private String airtableToken;

    @Value("${cloud.aws.credentials.access-key}")
    private String s3AccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String s3SecretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String s3Bucket;
}
