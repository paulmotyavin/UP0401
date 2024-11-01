--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4
-- Dumped by pg_dump version 15.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    id bigint NOT NULL,
    description character varying(1000),
    name character varying(35)
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.categories ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.events (
    id bigint NOT NULL,
    censorship character varying(3),
    date date,
    description character varying(1000),
    name character varying(50),
    id_category bigint,
    id_organizer bigint,
    id_place bigint,
    id_sponsor bigint
);


ALTER TABLE public.events OWNER TO postgres;

--
-- Name: events_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.events ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: organizers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organizers (
    id bigint NOT NULL,
    contacts character varying(50),
    name character varying(35),
    surname character varying(45)
);


ALTER TABLE public.organizers OWNER TO postgres;

--
-- Name: organizers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.organizers ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.organizers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: places; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.places (
    id bigint NOT NULL,
    address character varying(150),
    contacts character varying(50),
    name character varying(70)
);


ALTER TABLE public.places OWNER TO postgres;

--
-- Name: places_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.places ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.places_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: registrations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.registrations (
    id bigint NOT NULL,
    registration_date timestamp(6) without time zone,
    id_event bigint,
    id_user bigint
);


ALTER TABLE public.registrations OWNER TO postgres;

--
-- Name: registrations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.registrations ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.registrations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: reviews; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reviews (
    id bigint NOT NULL,
    comment character varying(1000),
    date timestamp(6) without time zone,
    id_event bigint,
    id_user bigint
);


ALTER TABLE public.reviews OWNER TO postgres;

--
-- Name: reviews_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.reviews ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.reviews_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sponsors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sponsors (
    id bigint NOT NULL,
    contacts character varying(50),
    name character varying(50)
);


ALTER TABLE public.sponsors OWNER TO postgres;

--
-- Name: sponsors_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.sponsors ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sponsors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    is_active boolean NOT NULL,
    birth_date date,
    name character varying(35) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(25) NOT NULL,
    surname character varying(45) NOT NULL,
    username character varying(25) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories (id, description, name) FROM stdin;
4	ergerg	ergerg
7	Все музыкальные события	Музыка
8	Выставки и арт-мероприятия	Искусство
9	Спортивные соревнования	Спорт
\.


--
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.events (id, censorship, date, description, name, id_category, id_organizer, id_place, id_sponsor) FROM stdin;
1	12+	2025-10-09	Концер группы Руки Вверх, зовите всех своих родных и близких	Концерт Руки Вверх	7	4	1	3
2	6+	2024-11-02	Соревнования по футолбу с участием многих фристайлеров и блогеров	Спорт фестиваль	9	1	8	1
\.


--
-- Data for Name: organizers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.organizers (id, contacts, name, surname) FROM stdin;
1	contact@petrov.com	Петр	Петров
2	@ivaongg	Иван	Иванов
4	+7 (495) 111-22-33	Сергей	Васильев
\.


--
-- Data for Name: places; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.places (id, address, contacts, name) FROM stdin;
1	ул. Пушкина, д. 10	contact@concertplace.com	Концертный зал
5	пр. Ленина, д. 20	+7 (495) 234-56-78	Галерея искусств
8	ул. Спорта, д. 5	+7 (495) 345-67-89	Спортивный комплекс
\.


--
-- Data for Name: registrations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.registrations (id, registration_date, id_event, id_user) FROM stdin;
1	2024-10-31 14:55:01.945784	1	2
16	2024-10-31 15:53:51.734098	1	5
\.


--
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reviews (id, comment, date, id_event, id_user) FROM stdin;
3	Замечательно просто!	2024-10-31 09:18:24.306835	1	5
5	вообще супер!	2024-10-31 13:35:07.360243	1	5
7	Крутааа	2024-11-01 14:29:06.240391	1	7
4	Вроде круто прошло	2024-11-01 14:49:17.014262	1	5
\.


--
-- Data for Name: sponsors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sponsors (id, contacts, name) FROM stdin;
1	+7 (495) 666-77-88	Nike
3	pepsipr@gmail.com	Pepsi
4	gazprom@yandex.ru	Gazprom
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, is_active, birth_date, name, password, role, surname, username) FROM stdin;
5	t	2005-07-10	Павел	$2a$10$hQbGHabEkn9iVoq4EXZVU.7EMSOnQ9LKK/erYK2yf.R8rerp95kVy	ADMIN	Мотявин	paulscriptum
1	t	1990-01-01	Петр	securepassword	ADMIN	Иванов	johndoe
7	t	2010-07-10	Петр	$2a$10$As9EKudwyNkMAUKOzVLvrefswHS1hc4.TabhZ.l9jViQGr85cf1nO	USER	Иванов	ivanov
2	t	1990-01-01	Василий	123	USER	Денисов	john
\.


--
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categories_id_seq', 9, true);


--
-- Name: events_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.events_id_seq', 2, true);


--
-- Name: organizers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.organizers_id_seq', 4, true);


--
-- Name: places_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.places_id_seq', 8, true);


--
-- Name: registrations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.registrations_id_seq', 17, true);


--
-- Name: reviews_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reviews_id_seq', 7, true);


--
-- Name: sponsors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sponsors_id_seq', 4, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 7, true);


--
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- Name: organizers organizers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organizers
    ADD CONSTRAINT organizers_pkey PRIMARY KEY (id);


--
-- Name: places places_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.places
    ADD CONSTRAINT places_pkey PRIMARY KEY (id);


--
-- Name: registrations registrations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registrations
    ADD CONSTRAINT registrations_pkey PRIMARY KEY (id);


--
-- Name: reviews reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (id);


--
-- Name: sponsors sponsors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sponsors
    ADD CONSTRAINT sponsors_pkey PRIMARY KEY (id);


--
-- Name: registrations ukqw1np29r4eyd8j8okevlwysj3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registrations
    ADD CONSTRAINT ukqw1np29r4eyd8j8okevlwysj3 UNIQUE (id_user);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: registrations fk19vat6vg6o1rv5ee3tcst8k27; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registrations
    ADD CONSTRAINT fk19vat6vg6o1rv5ee3tcst8k27 FOREIGN KEY (id_user) REFERENCES public.users(id);


--
-- Name: reviews fk2ep6x0b0wotn5ik1djjfxblk5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT fk2ep6x0b0wotn5ik1djjfxblk5 FOREIGN KEY (id_user) REFERENCES public.users(id);


--
-- Name: reviews fk653bw45dwxeqaaw9kn151yfck; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT fk653bw45dwxeqaaw9kn151yfck FOREIGN KEY (id_event) REFERENCES public.events(id);


--
-- Name: events fk8gx13k1k0jwvgi8q8jxjp8y5i; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fk8gx13k1k0jwvgi8q8jxjp8y5i FOREIGN KEY (id_organizer) REFERENCES public.organizers(id);


--
-- Name: registrations fkd4nlpvin5nnffv04ciw55x49y; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registrations
    ADD CONSTRAINT fkd4nlpvin5nnffv04ciw55x49y FOREIGN KEY (id_event) REFERENCES public.events(id);


--
-- Name: events fknehc03e7xvs4u5ef9i063vml3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fknehc03e7xvs4u5ef9i063vml3 FOREIGN KEY (id_category) REFERENCES public.categories(id);


--
-- Name: events fkpc0f7fsgk2v6rlj37dsis9ivj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fkpc0f7fsgk2v6rlj37dsis9ivj FOREIGN KEY (id_place) REFERENCES public.places(id);


--
-- Name: events fkpdx23uxhrdt9jhp6e8w9m08ut; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fkpdx23uxhrdt9jhp6e8w9m08ut FOREIGN KEY (id_sponsor) REFERENCES public.sponsors(id);


--
-- PostgreSQL database dump complete
--

