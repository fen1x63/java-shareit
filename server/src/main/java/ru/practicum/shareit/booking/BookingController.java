package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.BookingDtoDefault;
import ru.practicum.shareit.booking.model.BookingDtoOutgoing;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDtoOutgoing postBooking(@RequestHeader("X-Sharer-User-Id") Integer userId, @RequestBody BookingDtoDefault bookingDtoDefault) {
        log.info("Получен запрос на добавление бронирования пользователем с ID = {}", userId);
        return bookingService.postBooking(bookingDtoDefault, userId);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoOutgoing getBooking(@RequestHeader("X-Sharer-User-Id") Integer userId, @PathVariable Integer bookingId) {
        log.info("Получен запрос на получение бронирования с ID = {}", bookingId);
        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping
    public List<BookingDtoOutgoing> getUserBookings(
            @RequestHeader("X-Sharer-User-Id") int userId,
            @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Получен запрос на получение всех бронирований пользователя с Id = {}", userId);
        return bookingService.getUserBookings(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingDtoOutgoing> getOwnerBookings(
            @RequestHeader("X-Sharer-User-Id") int userId,
            @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Получен запрос на получение всех бронирований владельца с ID = {}", userId);
        return bookingService.getOwnerBookings(userId, state, from, size);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoOutgoing putBooking(@RequestHeader("X-Sharer-User-Id") Integer userId, @PathVariable Integer bookingId, @RequestParam boolean approved) {
        log.info("Получен запрос на обновление бронирования с ID = {}", bookingId);
        return bookingService.putBooking(userId, bookingId, approved);
    }
}
