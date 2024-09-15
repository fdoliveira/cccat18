drop schema if exists ccca cascade;

create schema ccca;

create table ccca.account(
	account_id uuid primary key,
	name text not null,
	email text not null unique,
	cpf text not null,
	is_passenger boolean not null default false,
	is_driver boolean not null default false,
	car_plate text null,
	password text not null
);

create table ccca.ride(
	ride_id uuid primary key,
	passenger_id uuid not null,
    from_lat double precision not null,
	from_long double precision not null,
    to_lat double precision not null,
	to_long double precision not null,
    status text not null,
    driver_id uuid null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    foreign key (passenger_id) references ccca.account(account_id),
    foreign key (driver_id) references ccca.account(account_id)
);