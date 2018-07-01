drop schema if exists RocketLaunch;

create schema RocketLaunch;

use RocketLaunch;

create table Fuel (
	fuelId int auto_increment primary key,
    `name` varchar(30),
	fuelDuration decimal(5,2),
    density decimal(5,2)
);

create table Body (
	bodyId int auto_increment primary key,
    `name` varchar(30),
	windResistance decimal(5,2),
    mass decimal(5,2)
);

create table `Engine` (
	engineId int auto_increment primary key,
	`name` varchar(30),
	thrust decimal(5,1),
	mass decimal(5,2)
);

create table Rocket (
	rocketId int auto_increment primary key,
    `name` varchar(30),
	`engine` int references RocketEngine(engineId),
    body int references Body(bodyId),
    fuel int references Fuel(fuelId)
);

create table Location (
	locationId int auto_increment primary key,
    `name` varchar(30),
    url varchar(30)
);

create table Launch (
	launchId int auto_increment primary key,
    angle decimal(5,1),
    distance decimal(7,1),
    `date` Date,
	location int references Location(locationId),
    rocket int references Rocket(rocketId)
);


insert into Location (`name`, url)
values ('Minneapolis', 'minneapolis-photo.jpg'),
('Chicago', 'chicago-photo.jpg'),
('Duluth', 'duluth-minnesota.jpg');

insert into Fuel (`name`, fuelDuration, density)
values ('Hydrogen', .8, 5),
('Petroleum', .7, 5.1),
('Diesel', .9, 4.9);

insert into Body (`name`, windResistance, mass)
values ('Atlas', .9, 45),
('Thor', .95, 48),
('Scout', .85, 42);

insert into `Engine` (`name`, thrust, mass)
values ('Ion', 3200, 45),
('Cold-gas', 3300, 48),
('Solid fuel', 3100, 42);

insert into Rocket (`name`, `engine`, body, fuel) values
('Bob', 1, 1, 1); /*, ('Steve', 3, 2, 1);*/

insert into Launch (angle, distance, `date`, location, rocket) values
(0.0, 0.0, '1999-12-31', 1, 1);
/*
(15.0, 120.0, '1999-12-31', 1, 2),
(30.0, 150.0, '1999-12-31', 2, 1),
(45.0, 180.0, '1999-12-31', 3, 2);
*/

select * from `Engine`;
select * from Location;
select * from Rocket;
select * from Launch;
