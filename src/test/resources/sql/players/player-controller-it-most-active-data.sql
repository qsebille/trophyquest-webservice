TRUNCATE TABLE app.trophy CASCADE;
TRUNCATE TABLE app.earned_trophy CASCADE;
TRUNCATE TABLE app.played_game CASCADE;
TRUNCATE TABLE app.player CASCADE;

INSERT INTO app.player (id, pseudo, avatar_url)
VALUES ('00000001-0000-0000-0000-000000000000', 'PlayerOne', 'http://avatar1'),
       ('00000002-0000-0000-0000-000000000000', 'PlayerTwo', 'http://avatar2'),
       ('00000003-0000-0000-0000-000000000000', 'PlayerThree', 'http://avatar3')
;

INSERT INTO app.game (id, title, platform, image_url)
VALUES ('00000000-0001-0000-0000-000000000000', 'GameOne', 'PS5', 'http://game1')
;

INSERT INTO app.trophy (id, game_id, game_group_id, rank, title, description, trophy_type, is_hidden, icon_url)
VALUES ('00000000-0000-0001-0000-000000000000', '00000000-0001-0000-0000-000000000000', 'default', 1, 'TrophyOne',
        'Trophy 1', 'gold', false, 'trophy1.png'),
       ('00000000-0000-0002-0000-000000000000', '00000000-0001-0000-0000-000000000000', 'default', 1, 'TrophyTwo',
        'Trophy 2', 'gold', false, 'trophy2.png')
;


INSERT INTO app.earned_trophy (player_id, trophy_id, earned_at)
VALUES ('00000001-0000-0000-0000-000000000000', '00000000-0000-0001-0000-000000000000', '2021-01-01 00:00:00'),
       ('00000002-0000-0000-0000-000000000000', '00000000-0000-0001-0000-000000000000', '2021-01-02 00:00:00'),
       ('00000002-0000-0000-0000-000000000000', '00000000-0000-0002-0000-000000000000', '2021-01-02 00:00:00')
;

UPDATE app.earned_trophy
SET earned_at = now() - interval '1 day'
WHERE player_id IN ('00000001-0000-0000-0000-000000000000',
                    '00000002-0000-0000-0000-000000000000');

UPDATE app.earned_trophy
SET earned_at = now() - interval '8 days'
WHERE player_id = '00000003-0000-0000-0000-000000000000';

