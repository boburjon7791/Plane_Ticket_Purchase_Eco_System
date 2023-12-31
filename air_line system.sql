-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://redmine.postgresql.org/projects/pgadmin4/issues/new if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS system_of_airline.activate_codes
(
    code integer NOT NULL,
    valid timestamp(6) without time zone NOT NULL,
    auth_user_id uuid NOT NULL,
    CONSTRAINT activate_codes_pkey PRIMARY KEY (code)
);

CREATE TABLE IF NOT EXISTS system_of_airline.airport
(
    id uuid NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    company_id uuid NOT NULL,
    CONSTRAINT airport_pkey PRIMARY KEY (id),
    CONSTRAINT uk_q56suuesv126soe4jfqsym4c7 UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS system_of_airline.auth_user
(
    id uuid NOT NULL,
    blocked boolean NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role character varying(255) COLLATE pg_catalog."default" NOT NULL,
    company_id uuid,
    CONSTRAINT auth_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_klvc3dss72qnlrjp2bai055mw UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS system_of_airline.auth_user_flight
(
    auth_user_id uuid NOT NULL,
    flight_id uuid NOT NULL,
    CONSTRAINT auth_user_flight_pkey PRIMARY KEY (auth_user_id, flight_id)
);

CREATE TABLE IF NOT EXISTS system_of_airline.city
(
    id uuid NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    gmt character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT city_pkey PRIMARY KEY (id),
    CONSTRAINT uk_qsstlki7ni5ovaariyy9u8y79 UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS system_of_airline.city_airport
(
    city_id uuid NOT NULL,
    airport_id uuid NOT NULL,
    CONSTRAINT city_airport_pkey PRIMARY KEY (city_id, airport_id)
);

CREATE TABLE IF NOT EXISTS system_of_airline.company
(
    id uuid NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT company_pkey PRIMARY KEY (id),
    CONSTRAINT uk_niu8sfil2gxywcru9ah3r4ec5 UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS system_of_airline.flight
(
    id uuid NOT NULL,
    from_time timestamp(6) without time zone NOT NULL,
    to_time timestamp(6) without time zone NOT NULL,
    airport_id uuid NOT NULL,
    from_id uuid NOT NULL,
    to_id uuid NOT NULL,
    price double precision,
    CONSTRAINT flight_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS system_of_airline.activate_codes
    ADD CONSTRAINT fk6qy5dtqdqqoa8hkkdryr3si8o FOREIGN KEY (auth_user_id)
    REFERENCES system_of_airline.auth_user (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.airport
    ADD CONSTRAINT fk76x9yoywyofwn5a8gu2lcoukx FOREIGN KEY (company_id)
    REFERENCES system_of_airline.company (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.auth_user
    ADD CONSTRAINT fkaaujysirtbormamlp0at3r5io FOREIGN KEY (company_id)
    REFERENCES system_of_airline.company (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.auth_user_flight
    ADD CONSTRAINT fk6ab7f88pcx5vi95w9a6hoosso FOREIGN KEY (flight_id)
    REFERENCES system_of_airline.auth_user (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.auth_user_flight
    ADD CONSTRAINT fklpda6ahpt64mesd07ub0mym58 FOREIGN KEY (auth_user_id)
    REFERENCES system_of_airline.flight (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.city_airport
    ADD CONSTRAINT fk8unkh7j598ao9uc96vqs4ajd2 FOREIGN KEY (city_id)
    REFERENCES system_of_airline.city (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.city_airport
    ADD CONSTRAINT fkpgxuj85g3b49qrjsg7c875rji FOREIGN KEY (airport_id)
    REFERENCES system_of_airline.airport (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.flight
    ADD CONSTRAINT fk18wyn8lxh4ewvbq6meexu4pvp FOREIGN KEY (to_id)
    REFERENCES system_of_airline.city (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.flight
    ADD CONSTRAINT fk7gkoynfju9y1d2864ffy3pvqg FOREIGN KEY (from_id)
    REFERENCES system_of_airline.city (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.flight
    ADD CONSTRAINT fk806y3y5ikum10nt8x9fbxbk3n FOREIGN KEY (airport_id)
    REFERENCES system_of_airline.airport (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;