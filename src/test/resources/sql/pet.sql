SET REFERENTIAL_INTEGRITY FALSE;

INSERT INTO favorite (created_at, updated_at, total, day_increase_count, can_increase)
VALUES ('2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000', 0, 0, true);

INSERT INTO pet (created_at, updated_at, favorite_id, image_id, member_id, name, species, owner, death_anniversary)
VALUES ('2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000', 1, null, 2, '두부', '고양이', '형님', '2023-01-01');

INSERT INTO pet_personality (pet_id, personality)
VALUES (1, '활발한');

SET REFERENTIAL_INTEGRITY TRUE;