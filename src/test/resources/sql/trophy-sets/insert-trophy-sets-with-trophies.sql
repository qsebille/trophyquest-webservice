insert into app.trophy_set(id, title, platform)
values ('00000001-0000-0000-0000-000000000000', 'Outer Wilds', 'PS5')
;

insert into app.trophy(id, trophy_set_id, title, description, trophy_type, rank, is_hidden, game_group_id)
values ('00000001-000-0000-0000-000000000000', '00000001-0000-0000-0000-000000000000', 'First Trophy', '', 'platinum',
        0, false, ''),
       ('00000002-000-0000-0000-000000000000', '00000001-0000-0000-0000-000000000000', 'Second Trophy', '', 'gold', 1,
        true, '')
;