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
    driver_id uuid null,
    status text not null,
    fare numeric null,
    distance numeric null,
    from_lat numeric not null,
	from_long numeric not null,
    to_lat numeric not null,
	to_long numeric not null,
    date timestamp not null,
    foreign key (passenger_id) references ccca.account(account_id),
    foreign key (driver_id) references ccca.account(account_id)
);