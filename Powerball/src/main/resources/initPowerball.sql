drop schema if exists KyleGagPowerball;

create schema KyleGagPowerball;

use KyleGagPowerball;

create table Pick (
	pickId int auto_increment primary key,
    firstName varchar(30),
    lastName varchar(30),
    email varchar(50),
	state char(2),
    firstNum decimal(2,0),
	secondNum decimal(2,0),
	thirdNum decimal(2,0),
	fourthNum decimal(2,0),
    fifthNum decimal(2,0),
    powerBall decimal(2,0),
    isQuickPick boolean,
    isValid boolean,
    pickDate date
);

insert into Pick (firstName, LastName)
values ('Bob', null), (null, 'Joe');

select * from pick;

update pick set 
isValid = true
where 0 < pickId;

select * from pick 
where (firstName is null OR firstName like '%')
and (lastName is null OR lastName like 'b')
and (email is null OR email like 'c')
and (state is null OR state = 'd');


select * from pick 
where (firstName is null OR firstName like '%')
and (lastName is null OR lastName like '%')
and (email is null OR email like '%')
and (state is null OR state like '%');