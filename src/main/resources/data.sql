INSERT INTO users (
    created_at,
    created_by,
    updated_at,
    updated_by,
    email,
    username,
    password,
    role,
    is_deleted,
    is_active
)
VALUES (
           now(),
           1,
           now(),
           1,
           'me@wasiif.me',
           'root',
           '$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe0NWrAFRdyW7cxbVdswOCBxB85D1RKcC',
           'ADMIN',
           false,
           true
       );

--user name is root and password is admin