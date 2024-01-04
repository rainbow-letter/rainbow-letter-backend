SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO image (created_at, updated_at, bucket, object_key, type, url)
VALUES ('2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000', 'rainbowletter', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'PET', 'http://rainbowletter/image');

INSERT INTO favorite (created_at, updated_at, total, day_increase_count, can_increase, last_increase_at)
VALUES ('2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000', 0, 0, true, '2023-01-01 12:00:00.000000');

INSERT INTO favorite (created_at, updated_at, total, day_increase_count, can_increase, last_increase_at)
VALUES ('2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000', 0, 0, true, '2023-01-01 12:00:00.000000');

INSERT INTO pet (created_at, updated_at, favorite_id, image_id, member_id, name, species, owner, death_anniversary)
VALUES ('2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000', 1, 1, 2, '콩이', '고양이', '형님', '2023-01-01');

INSERT INTO pet (created_at, updated_at, favorite_id, image_id, member_id, name, species, owner, death_anniversary)
VALUES ('2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000', 2, null, 2, '미키', '강아지', '엄마', null);

INSERT INTO pet_personality (pet_id, personality)
VALUES (1, '활발한');

INSERT INTO pet_personality (pet_id, personality)
VALUES (1, '잘삐짐');

SET FOREIGN_KEY_CHECKS = 1;