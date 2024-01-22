SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO image (created_at, updated_at, bucket, object_key, type, url)
VALUES ('2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000', 'rainbowletter',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'LETTER', 'http://rainbowletter/image');

INSERT INTO image (created_at, updated_at, bucket, object_key, type, url)
VALUES ('2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000', 'rainbowletter',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'LETTER', 'http://rainbowletter/image');

INSERT INTO chat_gpt (completion_tokens, prompt_tokens, total_tokens, created_at, updated_at, content)
VALUES (300, 100, 400, '2023-01-02 12:00:00.000000', '2023-01-02 12:00:00.000000', 'ChatGpt가 생성한 컨텐츠');

INSERT INTO chat_gpt (completion_tokens, prompt_tokens, total_tokens, created_at, updated_at, content)
VALUES (300, 100, 400, '2023-01-02 12:00:00.000000', '2023-01-02 12:00:00.000000', 'ChatGpt가 생성한 컨텐츠');

INSERT INTO chat_gpt (completion_tokens, prompt_tokens, total_tokens, created_at, updated_at, content)
VALUES (300, 100, 400, '2023-01-02 12:00:00.000000', '2023-01-02 12:00:00.000000', 'ChatGpt가 생성한 컨텐츠');

INSERT INTO reply (chat_gpt_id, created_at, timestamp, updated_at, summary, content, read_status, type, inspection,
                   inspection_time)
VALUES (1, '2023-01-02 12:00:00.000000', '2023-01-02 12:00:00.000000', '2023-01-02 12:00:00.000000', '엄마 미키 여기서 잘 지내!',
        '엄마 미키 여기서 잘 지내! 여기 무지개마을은 매일 햇살이 따뜻해. 미키 언제나 엄마 곁에 있을게. 사랑해!', 'UNREAD', 'REPLY', true,
        '2023-01-02 12:00:00.000000');

INSERT INTO reply (chat_gpt_id, created_at, timestamp, updated_at, summary, content, read_status, type, inspection)
VALUES (2, '2023-01-02 12:00:00.000000', '2023-01-02 12:00:00.000000', '2023-01-02 12:00:00.000000', '엄마 콩이야!',
        '엄마 콩이야! 콩이는 무지개마을에서 따듯한 햇살을 느끼고있어. 사랑해!', 'UNREAD', 'CHAT_GPT', false);

INSERT INTO reply (chat_gpt_id, created_at, timestamp, updated_at, summary, content, read_status, type, inspection,
                   inspection_time)
VALUES (3, '2023-01-02 12:00:00.000000', '2023-01-02 12:00:00.000000', '2023-01-02 12:00:00.000000', '엄마 콩이야!',
        '엄마 콩이야! 콩이는 무지개마을에서 따듯한 햇살을 느끼고있어. 사랑해!', 'UNREAD', 'CHAT_GPT', true, '2023-01-02 12:00:00.000000');

INSERT INTO letter (created_at, image_id, pet_id, reply_id, updated_at, summary, content, status, share_link)
VALUES ('2023-01-01 12:00:00.000000', 2, 2, 1, '2023-01-01 12:00:00.000000', '미키야 엄마가 보고싶다.',
        '미키야 엄마가 보고싶다. 엄마는 오늘 미키 생각하면서 그림을 그렸어.', 'RESPONSE', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa');

INSERT INTO letter (created_at, image_id, pet_id, reply_id, updated_at, summary, content, status, share_link)
VALUES ('2023-01-02 12:00:00.000000', null, 1, 2, '2023-01-02 12:00:00.000000', '콩아 형님이다.', '콩아 형님이다. 형님이 오늘 한잔했다.',
        'RESPONSE', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb');

INSERT INTO letter (created_at, image_id, pet_id, reply_id, updated_at, summary, content, status, share_link)
VALUES ('2023-01-03 12:00:00.000000', null, 1, 3, '2023-01-02 12:00:00.000000', '콩아 형님이다.', '콩아 형님이다. 형님이 오늘 한잔했다.',
        'REQUEST', 'cccccccc-cccc-cccc-cccc-cccccccccccc');

SET FOREIGN_KEY_CHECKS = 1;