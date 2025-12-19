TRUNCATE TABLE app.trophy CASCADE;
TRUNCATE TABLE app.earned_trophy CASCADE;
TRUNCATE TABLE app.played_game CASCADE;
TRUNCATE TABLE app.player CASCADE;

INSERT INTO app.player (id, pseudo, avatar_url)
VALUES ('00000001-0000-0000-0000-000000000000', 'PlayerOne', 'http://avatar1'),
       ('00000002-0000-0000-0000-000000000000', 'PlayerTwo', 'http://avatar2'),
       ('00000003-0000-0000-0000-000000000000', 'PlayerWithoutTrophy', 'http://avatar3')
;

INSERT INTO app.game (id, title, platform, image_url)
VALUES ('00000000-0001-0000-0000-000000000000', 'GameOne', 'PS5', 'http://game1')
;

INSERT INTO app.trophy (id, game_id, game_group_id, rank, title, description, trophy_type, is_hidden, icon_url)
VALUES ('00000000-0000-0001-0000-000000000000', '00000000-0001-0000-0000-000000000000', 'default', 1, 'TrophyOne',
        'TrophyOneDescription', 'gold', false, 'http://trophy1')
;

-- The API returns only players with at least one trophy
-- Players are sorted by the last earned game timestamp
INSERT INTO app.earned_trophy (player_id, trophy_id, earned_at)
VALUES ('00000001-0000-0000-0000-000000000000', '00000000-0000-0001-0000-000000000000', '2021-01-01 00:00:00'),
       ('00000002-0000-0000-0000-000000000000', '00000000-0000-0001-0000-000000000000', '2021-01-02 00:00:00')
;

INSERT INTO app.played_game (player_id, game_id, last_played_at)
VALUES ('00000001-0000-0000-0000-000000000000', '00000000-0001-0000-0000-000000000000', '2021-01-01 00:00:00'),
       ('00000002-0000-0000-0000-000000000000', '00000000-0001-0000-0000-000000000000', '2021-01-01 00:00:00')
;

