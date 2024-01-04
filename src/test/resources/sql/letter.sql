SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO image (created_at, updated_at, bucket, object_key, type, url)
VALUES ('2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000', 'rainbowletter', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'LETTER', 'http://rainbowletter/image');

INSERT INTO image (created_at, updated_at, bucket, object_key, type, url)
VALUES ('2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000', 'rainbowletter', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'LETTER', 'http://rainbowletter/image');

INSERT INTO letter (created_at, image_id, pet_id, reply_id, updated_at, summary, content, status)
VALUES ('2023-01-01 12:00:00.000000', 2, 2, null, '2023-01-01 12:00:00.000000', '미키야 엄마가 보고싶다.', '미키야 엄마가 보고싶다. 엄마는 오늘 미키 생각하면서 그림을 그렸어.', 'REQUEST');

INSERT INTO letter (created_at, image_id, pet_id, reply_id, updated_at, summary, content, status)
VALUES ('2023-01-02 12:00:00.000000', null, 1, null, '2023-01-02 12:00:00.000000', '콩아 형님이다.', '콩아 형님이다. 형님이 오늘 한잔했다.', 'REQUEST');

SET FOREIGN_KEY_CHECKS = 1;