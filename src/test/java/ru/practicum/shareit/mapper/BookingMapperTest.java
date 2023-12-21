package ru.practicum.shareit.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.item.model.item.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingMapperTest {

    @Test
    public void testBookingToDtoOutgoing() {
        User booker = new User(1, "user1", "user1@email.com");
        Item item = new Item(1,"Test item","Test item description",true,booker,null);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.parse("2023-12-01T09:00:00"));
        booking.setEnd(LocalDateTime.parse("2023-12-05T18:00:00"));
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(BookingStatus.APPROVED);

        BookingDtoOutgoing dto = BookingMapper.bookingToDtoOutgoing(booking);

        assertEquals(1, dto.getId());
        assertEquals(LocalDateTime.parse("2023-12-01T09:00:00"), dto.getStart());
        assertEquals(LocalDateTime.parse("2023-12-05T18:00:00"), dto.getEnd());
        assertEquals(BookingStatus.APPROVED, dto.getStatus());
    }

    @Test
    public void testBookingFromDto() {
        User booker = new User(1, "user1", "user1@email.com");
        Item item = new Item(1, "Test item", "Test item description", true, booker, null);

        BookingDtoDefault dto = new BookingDtoDefault(1, LocalDateTime.parse("2023-12-01T09:00:00"),
                LocalDateTime.parse("2023-12-05T18:00:00"), 2, 3, BookingStatus.APPROVED);

        Booking booking = BookingMapper.bookingFromDto(dto, booker, item);

        assertEquals(1, booking.getId());
        assertEquals(LocalDateTime.parse("2023-12-01T09:00:00"), dto.getStart());
        assertEquals(LocalDateTime.parse("2023-12-05T18:00:00"), dto.getEnd());
        assertEquals(item, booking.getItem());
        assertEquals(booker, booking.getBooker());
        assertEquals(BookingStatus.APPROVED, booking.getStatus());
    }

    @Test
    public void testBookingToDtoDefault() {
        User booker = new User(3, "user1", "user1@email.com");
        Item item = new Item(2, "Test item", "Test item description", true, booker, null);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.parse("2023-12-01T09:00:00"));
        booking.setEnd(LocalDateTime.parse("2023-12-05T18:00:00"));
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(BookingStatus.APPROVED);

        BookingDtoDefault dto = BookingMapper.bookingToDtoDefault(booking);

        assertEquals(1, dto.getId());
        assertEquals(LocalDateTime.parse("2023-12-01T09:00:00"), dto.getStart());
        assertEquals(LocalDateTime.parse("2023-12-05T18:00:00"), dto.getEnd());
        assertEquals(2, dto.getItemId());
        assertEquals(3, dto.getBookerId());
        assertEquals(BookingStatus.APPROVED, dto.getStatus());
    }
}