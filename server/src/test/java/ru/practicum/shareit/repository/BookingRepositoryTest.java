package ru.practicum.shareit.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.MemoryBooking;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.MemoryUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingRepositoryTest {

    private final Pageable page = PageRequest.of(0, 10, Sort.unsorted());

    private final MemoryBooking memoryBooking;
    private final MemoryUser memoryUser;

    @BeforeEach
    public void setUp() {
        User user = new User(null, "user", "user@email.com");
        memoryUser.save(user);
    }

    @Test
    public void testFindByBookerIdIsEmpty() {
        int bookerId = 1;
        List<Booking> bookings = memoryBooking.findByBookerIdOrderByStartDesc(bookerId, page);
        assertNotNull(bookings);
        assertTrue(bookings.isEmpty());
    }

    @Test
    public void testFindByItemOwnerIdIsEmpty() {
        int ownerId = 1;

        List<Booking> bookings = memoryBooking.findByItemOwnerIdOrderByStartDesc(ownerId, page);

        assertNotNull(bookings);
        assertTrue(bookings.isEmpty());
    }

}