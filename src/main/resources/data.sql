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
INSERT INTO public.employee_details (id, created_at, created_by, is_active, is_deleted, updated_at, updated_by, address, designation, full_name, mobile, picture, pp_mime_type, signature, signature_mime_type, user_id) VALUES (2, null, null, true, false, null, null, null, 'ADMINISTRATOR', 'ROOT', null, null, null, null, null, 1);
--user name is root and password is admin