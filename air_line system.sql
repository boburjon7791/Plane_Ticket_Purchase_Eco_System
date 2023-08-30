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

CREATE TABLE IF NOT EXISTS system_of_airline.airport_cities
(
    airport_id uuid NOT NULL,
    cities_id uuid NOT NULL,
    CONSTRAINT airport_cities_pkey PRIMARY KEY (airport_id, cities_id)
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
    flight_id uuid,
    CONSTRAINT auth_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_klvc3dss72qnlrjp2bai055mw UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS system_of_airline.city
(
    id uuid NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT city_pkey PRIMARY KEY (id),
    CONSTRAINT uk_qsstlki7ni5ovaariyy9u8y79 UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS system_of_airline.city_airports
(
    city_id uuid NOT NULL,
    airports_id uuid NOT NULL,
    CONSTRAINT city_airports_pkey PRIMARY KEY (city_id, airports_id)
);

CREATE TABLE IF NOT EXISTS system_of_airline.city_flights
(
    city_id uuid NOT NULL,
    flights_id uuid NOT NULL,
    CONSTRAINT city_flights_pkey PRIMARY KEY (city_id, flights_id)
);

CREATE TABLE IF NOT EXISTS system_of_airline.city_flights2
(
    city_id uuid NOT NULL,
    flights2_id uuid NOT NULL,
    CONSTRAINT city_flights2_pkey PRIMARY KEY (city_id, flights2_id)
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
    local_date_time timestamp(6) without time zone NOT NULL,
    airport_id uuid NOT NULL,
    CONSTRAINT flight_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS system_of_airline.flight_from
(
    flight_id uuid NOT NULL,
    from_id uuid NOT NULL,
    CONSTRAINT flight_from_pkey PRIMARY KEY (flight_id, from_id)
);

CREATE TABLE IF NOT EXISTS system_of_airline.flight_to
(
    flight_id uuid NOT NULL,
    to_id uuid NOT NULL,
    CONSTRAINT flight_to_pkey PRIMARY KEY (flight_id, to_id)
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


ALTER TABLE IF EXISTS system_of_airline.airport_cities
    ADD CONSTRAINT fkf8496cpnvbyw5pbrfqjpm68nj FOREIGN KEY (cities_id)
    REFERENCES system_of_airline.city (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.airport_cities
    ADD CONSTRAINT fkha69k2adxu495svgjsikscet1 FOREIGN KEY (airport_id)
    REFERENCES system_of_airline.airport (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.auth_user
    ADD CONSTRAINT fkaaujysirtbormamlp0at3r5io FOREIGN KEY (company_id)
    REFERENCES system_of_airline.company (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.auth_user
    ADD CONSTRAINT fkjo47m1sd8ipncve4paqmsky5n FOREIGN KEY (flight_id)
    REFERENCES system_of_airline.flight (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.city_airports
    ADD CONSTRAINT fk4jjd28mxuyh94kfk6iplwj4ds FOREIGN KEY (airports_id)
    REFERENCES system_of_airline.airport (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.city_airports
    ADD CONSTRAINT fk6a38952e1kstir0frbkudg1fy FOREIGN KEY (city_id)
    REFERENCES system_of_airline.city (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.city_flights
    ADD CONSTRAINT fkgfj27qi59ni9ica1pls2tr8jt FOREIGN KEY (city_id)
    REFERENCES system_of_airline.city (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.city_flights
    ADD CONSTRAINT fkglq16abw7dy5u8u9suo3hd88j FOREIGN KEY (flights_id)
    REFERENCES system_of_airline.flight (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.city_flights2
    ADD CONSTRAINT fk2jc4l4kbm5tuj9mjfpd5h0r23 FOREIGN KEY (city_id)
    REFERENCES system_of_airline.city (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.city_flights2
    ADD CONSTRAINT fkroq6ih1ewbkhs1mwxw0lbynfa FOREIGN KEY (flights2_id)
    REFERENCES system_of_airline.flight (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.flight
    ADD CONSTRAINT fk806y3y5ikum10nt8x9fbxbk3n FOREIGN KEY (airport_id)
    REFERENCES system_of_airline.airport (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.flight_from
    ADD CONSTRAINT fkc1hah8af0f94qhu531sn0q5ue FOREIGN KEY (from_id)
    REFERENCES system_of_airline.city (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.flight_from
    ADD CONSTRAINT fke7chw2e3ripuptqk4sc8rgyqx FOREIGN KEY (flight_id)
    REFERENCES system_of_airline.flight (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.flight_to
    ADD CONSTRAINT fk4ymiqktnib30thcw8w7qe54q8 FOREIGN KEY (to_id)
    REFERENCES system_of_airline.city (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS system_of_airline.flight_to
    ADD CONSTRAINT fk8jk0rrgsdj691r0tvl84ca9vi FOREIGN KEY (flight_id)
    REFERENCES system_of_airline.flight (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;