drop schema if exists ccca cascade;

create schema ccca;

create table ccca.account (
	account_id uuid primary key,
	name text not null,
	email text not null unique,
	cpf text not null,
	is_passenger boolean not null default false,
	is_driver boolean not null default false,
	car_plate text null,
	password text not null
);
