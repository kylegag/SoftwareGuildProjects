use HotelReservation;

-- 1.
select reservation.*
from reservation
left join billing on reservation.billingId = billing.billingId
where (reservation.billingId is null) && (reservation.endDate = curdate() + INTERVAL 1 DAY);

-- 2.

select Room.*
from PromotionCode
inner join Reservation 
on PromotionCode.PromotionCodeId = Reservation.PromotionCodeId
inner join RoomReservation
on RoomReservation.ReservationId = Reservation.ReservationId
inner join Room
on Room.RoomId = RoomReservation.RoomId
where PromotionCode.PromotionCodeId = 5;

-- 3.

select distinct Room.*, RoomRate.*
from Room
inner join RoomAmenities
on Room.RoomId = RoomAmenities.RoomId
inner join RoomRate
on RoomRate.RoomTypeId = RoomRate.RoomTypeId
where RoomAmenities.AmenitiesId != 2 
AND curdate() between RoomRate.StartDate and RoomRate.EndDate
order by RoomRate.OneDayRate;




