package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto postUser(@Valid @RequestBody UserDto user) {
        log.info("Получен запрос на добавление нового пользователя.");
        return userService.postUser(user);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Получен запрос на получение списка всех пользователей");
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        log.info("Получен запрос на получение пользователя с ID = {}", id);
        return userService.getUser(id);
    }

    @PatchMapping("/{id}")
    public UserDto putUser(@PathVariable Integer id, @RequestBody UserDto user) {
        log.info("Получен запрос на обновление пользователя с ID = {}", id);
        return userService.putUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void delUser(@PathVariable Integer id) {
        log.info("Получен запрос на удаление пользователя с ID = {}", id);
        userService.delUser(id);
    }

}
