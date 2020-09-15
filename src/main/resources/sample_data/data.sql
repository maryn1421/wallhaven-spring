


ALTER TABLE IF EXISTS ONLY public.favorites DROP CONSTRAINT IF EXISTS pk_favorites_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS fk_user_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.uploaded_wallpaper DROP CONSTRAINT IF EXISTS pk_uploaded_wallpaper_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS fk_user_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS pk_users_id CASCADE;



DROP TABLE IF EXISTS public.users;
CREATE TABLE users (
    id serial NOT NULL,
    friends integer[],
    email text,
    username text,
    password text,
    registration_date timestamp default current_timestamp
);


DROP TABLE IF EXISTS public.uploaded_wallpaper;
CREATE TABLE uploaded_wallpaper (
    id serial NOT NULL,
    username text,
    user_id integer,
    submission_time timestamp default current_timestamp
);


DROP TABLE IF EXISTS public.favorites;
CREATE TABLE favorites (
    id serial NOT NULL,
    user_id integer,
    submission_time timestamp default current_timestamp,
    wallpaper_id text
);

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users_id PRIMARY KEY (id);

ALTER TABLE ONLY uploaded_wallpaper
    ADD CONSTRAINT pk_uploaded_wallpaper_id PRIMARY KEY (id);

ALTER TABLE ONLY favorites
    ADD CONSTRAINT pk_favorites_id PRIMARY KEY (id);

ALTER TABLE ONLY favorites
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE ONLY uploaded_wallpaper
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;