insert into app.trophy_set(id, title, platform)
values ('11111111-0000-0000-0000-000000000000', 'Outer Wilds', 'PS5')
;

insert into app.igdb_game(id, name)
values (1, 'Outer Wilds'),
       (2, 'Outer Worlds'),
       (3, 'Outer Universe')
;

insert into app.igdb_candidate(trophy_set_id, candidate_id, score, status)
values ('11111111-0000-0000-0000-000000000000', 1, 100, 'PENDING'),
       ('11111111-0000-0000-0000-000000000000', 2, 90, 'PENDING'),
       ('11111111-0000-0000-0000-000000000000', 3, 80, 'NOT_PENDING')
;

