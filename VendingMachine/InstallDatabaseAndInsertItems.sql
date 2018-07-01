drop schema if exists KyleGagVendingMachineDatabase;

create schema KyleGagVendingMachineDatabase;

use KyleGagVendingMachineDatabase;

create table Inventory (
	ItemName varchar(30) primary key,
    ItemPrice decimal(5,2),
    ItemStock int
);

insert into inventory (ItemName, ItemPrice, ItemStock)
values ("Snickers", 5.20, 10),
("Twix", 2.00, 2),
("Prezels", 3.25, 3),
("Twinkie", 4.00, 3),
("Reese's Pieces", 3.00, 1),
("Hershey's", 2.95, 2),
("Trail Mix", 3.25, 0),
("Baby Ruth", 2.50, 1),
("Famous Amos", 4.05, 3);