package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.comment.CommentDto;
import ru.practicum.shareit.item.model.item.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto postItem(
            @RequestHeader("X-Sharer-User-Id") int userId,
            @RequestBody @Valid ItemDto itemDto
    ) {
        log.info("Получен запрос на добавление вещи по ее владельцу с ID = {}", userId);
        return itemService.postItem(itemDto, userId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto postComment(
            @RequestHeader("X-Sharer-User-Id") Integer userId,
            @PathVariable Integer itemId,
            @RequestBody @Valid CommentDto commentDto
    ) {
        log.info("Получен запрос на добавление комментария");
        return itemService.postComment(userId, itemId, commentDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(
            @RequestHeader("X-Sharer-User-Id") Integer userId,
            @PathVariable Integer itemId
    ) {
        log.info("Получен запрос на получение вещи с ID = {}", itemId);
        return itemService.getItem(userId, itemId);
    }

    @GetMapping
    public List<ItemDto> getItemsUser(
            @RequestHeader("X-Sharer-User-Id") int userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size
    ) {
        log.info("Получен запрос на получение всех вещей владельца с ID = {}", userId);
        return itemService.getItems(userId, from, size);
    }

    @GetMapping("/search")
    public List<ItemDto> getItems(
            @RequestParam String text,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size
    ) {
        log.info("Получен запрос на поиск вещей со следующим текстом: {}", text);
        return itemService.getItem(text, from, size);
    }

    @PatchMapping("/{itemId}")
    public ItemDto putItem(
            @RequestHeader("X-Sharer-User-Id") Integer userId,
            @PathVariable Integer itemId,
            @RequestBody ItemDto itemDto
    ) {
        log.info("Получен запрос на обновление вещей с ID = {}", itemId);
        return itemService.putItem(itemId, itemDto, userId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(
            @RequestHeader("X-Sharer-User-Id") Integer userId,
            @PathVariable Integer itemId
    ) {
        log.info("Получен запрос на удаление всех вещей с ID = {}", itemId);
        itemService.deleteItem(userId, itemId);
    }
}
