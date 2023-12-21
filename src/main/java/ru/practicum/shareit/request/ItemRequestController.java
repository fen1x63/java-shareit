package ru.practicum.shareit.request;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.model.RequestDto;
import ru.practicum.shareit.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class ItemRequestController {
    private final RequestService requestService;

    @PostMapping
    public RequestDto addItemRequest(
            @RequestHeader("X-Sharer-User-Id") int userId,
            @Valid @RequestBody RequestDto itemRequestDto) {
        log.info("Получен запрос на добавление новой вещи");
        return requestService.addItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    public List<RequestDto> getItemRequestsByUserId(@RequestHeader("X-Sharer-User-Id") int userId) {
        log.info("Получен запрос на получение списка запросов с данными об ответах на них");
        return requestService.getItemRequestsByUserId(userId);
    }

    @GetMapping(path = "/all")
    public List<RequestDto> getAllItemRequests(
            @RequestHeader("X-Sharer-User-Id") int userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size
    ) {
        log.info("Получен запрос на получение списка запросов");
        return requestService.getAllItemRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public RequestDto getItemRequestById(
            @RequestHeader("X-Sharer-User-Id") int userId,
            @PathVariable int requestId
    ) {
        log.info("Получен запрос на получение данных о запросе с id = " + requestId);
        return requestService.getItemRequestById(userId, requestId);
    }
}
