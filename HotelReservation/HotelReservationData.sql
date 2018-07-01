use HotelReservation;

insert into Customer (CustomerName, PhoneNumber, Email, CreditCard)
values ('Steve Levie', '504-403-2345', 'sLevi@gmail.com', '1234 4321 1234 4321'),
('Bob Jones', '403-123-4999', 'theJones@gmail.com', '9878 9876 9765 8211'),
('John Doe', '999-257-0015', 'jd92@gmail.com', '0303 2155 3333 0000'),
('Sarah Brown', '222-444-5555', 'sarahbrown@gmail.com', '0000 3455 9922 0202'),
('Bridget Anderson', '987-765-3456', 'banderson@gmail.com', '9999 8888 7777 6666');

insert into RoomType (RoomTypeName)
values ('double'), ('luxury suite'), ('single'), ('king');

insert into RoomRate (StartDate, EndDate, RoomTypeId, OneDayRate, HalfDayRate)
values ('2018-04-01','2019-04-01', 1, 100.00, 60.00),
('2018-01-01', '2019-01-01', 2, 215.00, 120.00), ('2019-01-01', '2020-01-01', 2, 200.00, 115.00), 
('2018-05-01', '2018-05-07', 3, 80.00, 45.00), ('2018-05-14', '2018-05-21', 3, 95.00, 55.00), ('2018-05-21', '2018-05-28', 3, 85.00 , 50.00),
('2018-05-01','2018-06-01', 4, 150.00, 85.00);



insert into Room (RoomNumber, Floor, Occupancy_Limit, RoomTypeId)
values (122, 1, 3, 1), (201, 2, 2, 3), (354, 3, 5, 4), (231, 2, 8, 2), (110, 1, 2, 3);
--      double         luxury suite    king            luxury suite     single

insert into Amenities (AmenitiesName)
values ('Hot tub'), ('XL TV'), ('Sauna'), ('Balcony');

insert into RoomAmenities (RoomId, AmenitiesId)
values (4,1), (4,2), (4, 3), (4,4), (1, 4), (3, 2), (3, 4);

insert into PromotionCode (StartDate, EndDate, DollarAmountOff, PercentOff)
values ('2018-05-03', '2018-05-10', null, 20.00), ('2018-05-10', '2018-05-17', 35.00, null),
('2018-05-17', '2018-05-24', 22.99, null), ('2018-05-24', '2018-05-31', null, 7.50),
('2018-04-01', '2018-06-01', 15.00, null);

insert into AddOn (AddOnName, StartDate, EndDate)
values ('Room service', '2018-03-15', '2018-04-14'), ('Room service', '2018-05-10', '2018-05-17'), 
('Room service', '2018-05-18', '2018-05-25'), ('Pay per view', '2018-05-01', '2018-06-01'),
('Pay per view', '2018-06-01', '2018-07-01');

insert into Billing (Total, Tax) 
values (350.00, 25.00), (240.00, 35.00), (190.00, 20.00), (160.00, 20.00), (110.00, 15.00), (220.00, 30.00);

insert into BillingDetails (RoomId, AddOnId, BillingId, Cost, Tax)
values (1, 2, 1, 20.00, 3.00), (2, 4, 1, 12.00, 2.00), (1, null, 1, 100.00, 15.00), (2, null, 1, 95.00, 12.50),
(3, null, 2, 150.00, 20.00),
(3, null, 3, 160.00, 23.00), (3, 2, 3, 19.99, 3.00),
(4, null, 4, 430.00, 20.00),
(1, null, 5, 100.00, 15.00), (1, 2, 5, 15.00, 3.00),
(2, null, 6, 80.00, 7.00), (2, 4, 6, 120.00, 20.00), (5, null, 6, 80.00, 7.00), (1, null, 6, 100.00, 17.00);

insert into Reservation (StartDate, EndDate, CustomerId, PromotionCodeId, BillingId)
values ('2018-04-11', '2018-04-12', 2, null, 1),
('2018-05-01', '2018-05-07', 4, null, 2),
('2018-04-11', '2018-04-12', 5, 5, 3),
('2018-05-12', '2018-05-14', 2, null, 4),
('2018-05-03', '2018-05-04', 1, null, 5),
('2018-04-30', '2018-05-01', 4, 5, 6),
('2018-07-10', '2018-07-12', 5, null, null),
('2018-05-19', '2018-05-21', 1, 3, null),
('2018-06-30', '2018-07-04', 3, null, null),
('2018-04-19', '2018-04-20', 2, null, null),

('2018-05-10', '2018-05-15', 2, null, 2),
('2018-05-10', '2018-05-15', 2, null, null);

insert into guest (GuestName, Age, ReservationId)
values ('Bob Hamilton', 33, 1), ('Steve Daniels', 20, 2), ('Sarah Wonder', 25, 3),
('Cynthia Row', 29, 4), ('Henry Roing', 12, 5), ('Bridget Rose', 44, 6),
('Ron Lenington', 60, 7), ('Trisha Fining', 19, 8), ('Nate Fog', 28, 9),
('Beth Johnson', 37, 10);

insert into RoomReservation (RoomId, ReservationId) 
values (1, 1), (2, 1), (3, 2), (3, 3), (4, 4), (1, 5), (2, 6);

insert into ReservationAddOn (AddOnId, ReservationId)
values (2, 1), (4, 1), (2, 3), (2, 5), (4, 6);
