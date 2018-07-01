

drop schema if exists HotelReservation;

create database HotelReservation;

use HotelReservation;

create table RoomType (
	RoomTypeId int primary key auto_increment,
    RoomTypeName varchar(30)
);

create table RoomRate (
	RoomRateId int primary key auto_increment,
    StartDate date,
    EndDate date,
    RoomTypeId int,
    OneDayRate decimal(5,2),
    HalfDayRate decimal(5,2),
    
    foreign key fk_RoomType (RoomTypeId) references RoomType(RoomTypeId)
);

create table Room (
	RoomId int primary key auto_increment,
    RoomNumber int,
    Floor int,
    Occupancy_Limit int,
    RoomTypeId int,
    
    foreign key fk_RoomType (RoomTypeId) references RoomType(RoomTypeId)
);

create table Amenities (
	AmenitiesId int primary key auto_increment,
    AmenitiesName varchar(30)
);

create table RoomAmenities (
	RoomId int,
    AmenitiesId int,

    primary key pk_RoomId_AmenitiesId (RoomId, AmenitiesId),
    foreign key fk_RoomId (RoomId) references Room(RoomId),
    foreign key fk_AmenitiesId (AmenitiesId) references Amenities(AmenitiesId)
);

create table AddOn (
	AddOnId int primary key auto_increment,
    AddOnName varchar(30),
    StartDate date,
    EndDate date
);

create table Billing (
	BillingId int primary key auto_increment,
    Total decimal(10,2),
    Tax decimal(10,2)
);

create table BillingDetails (
	RoomId int,
    AddOnId int,
    BillingId int,
    Cost decimal(10,2),
    Tax decimal(10,2),
    
	foreign key fk_RoomId (RoomId) references Room(RoomId),
    foreign key fk_AddOn (AddOnId) references AddOn(AddOnId),
	foreign key fk_BillingId (BillingId) references Billing(BillingId)
);

create table PromotionCode (
	PromotionCodeId int primary key auto_increment,
    StartDate date,
    EndDate date,
    DollarAmountOff decimal(10,2),
    PercentOff decimal(4,2)
);

create table Customer (
	CustomerId int primary key auto_increment,
    CustomerName varchar(50),
    PhoneNumber char(12),
    Email varchar(50),
    CreditCard char(19)
);

create table Reservation (
	ReservationId int primary key auto_increment,
    StartDate date,
    EndDate date,
    CustomerId int,
    PromotionCodeId int,
    BillingId int,
    
    foreign key fk_CustomerId (CustomerId) references Customer(CustomerId),
    foreign key fk_PromotionCodeId (PromotionCodeId) references PromotionCode(PromotionCodeId),
    foreign key fk_BillingId (BillingId) references Billing(BillingId)
);

create table Guest (
	GuestId int primary key auto_increment,
    GuestName varchar(30),
    Age int,
    ReservationId int,
    
    foreign key fk_ReservationId (ReservationId) references Reservation(ReservationId)
);

create table ReservationAddOn (
	AddOnId int,
    ReservationId int,
    
    primary key pk_AddOnId_ReservationId (AddOnId, ReservationId),
    foreign key fk_AddOnId (AddOnId) references AddOn(AddOnId),
    foreign key fk_ReservationId (ReservationId) references Reservation(ReservationId)
);

create table RoomReservation (
	RoomId int,
    ReservationId int,
    
    primary key pk_RoomReservation (RoomId, ReservationId),
    foreign key fk_RoomId (RoomId) references Room(RoomId),
    foreign key fk_ReservationId (ReservationId) references Reservation(ReservationId)
);

