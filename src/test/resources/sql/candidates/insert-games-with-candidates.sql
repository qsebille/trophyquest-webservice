INSERT INTO app.game(id, title, platform, image_url)
VALUES ('00000000-1111-0000-0000-000000000000', 'Tomb Raider', 'PS5', 'game1.png'),
       ('00000000-2222-0000-0000-000000000000', 'Outer Wilds', 'PS5', 'game2.png')
;

INSERT INTO app.igdb_game(id, name, cover, release_date)
VALUES (1, 'Tomb Raider 1', 'cover1.png', '2021-01-01'),
       (2, 'Tomb Raider 2', 'cover2.png', '2021-01-01'),
       (3, 'Tomb Raider 3', 'cover3.png', '2021-01-01'),
       (4, 'Outer Wilds', 'cover4.png', '2021-01-01')
;

INSERT INTO app.igdb_candidate(game_id, candidate_id, score, status)
VALUES ('00000000-1111-0000-0000-000000000000', 1, 100, 'PENDING'),
       ('00000000-1111-0000-0000-000000000000', 2, 90, 'PENDING'),
       ('00000000-1111-0000-0000-000000000000', 3, 90, 'PENDING'),
       ('00000000-2222-0000-0000-000000000000', 4, 100, 'PENDING')
;

