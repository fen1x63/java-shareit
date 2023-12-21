package ru.practicum.shareit.unit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.model.item.Item;
import ru.practicum.shareit.item.model.item.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.model.RequestDto;
import ru.practicum.shareit.request.service.RequestServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.repository.MemoryUser;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.practicum.shareit.request.model.RequestMapper.itemRequestFromDto;

@Transactional
@Rollback
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
class ItemServiceIntegrationTest {

    private final UserService userService;
    private final ItemService itemService;
    private final RequestServiceImpl requestService;
    private final UserDto userDto = new UserDto(1, "User1", "user@email.com");
    private final User user = new User(1, "User1", "user@email.com");
    private final ItemDto itemDto = ItemDto.builder()
            .id(1)
            .name("Test item")
            .description("Test item description")
            .isAvailable(true)
            .requestId(1)
            .build();
    private RequestDto requestDto = new RequestDto(1, "Test description",
            null, Collections.emptyList());
    private final Request itemRequest = itemRequestFromDto(requestDto, user);
    private final Item item = new Item(1, "Test item", "Test item description",
            true, user, itemRequest);
    @Autowired
    private MemoryUser memoryUser;


    @Test
    void testCreateItem() {
        int itemId = 1;
        int ownerId = 1;
        int requesterId = 1;
        memoryUser.save(user);
        requestService.addItemRequest(requesterId, requestDto);
        itemService.postItem(itemDto, ownerId);

        assertThat(item, notNullValue());
        assertThat(item.getId(), equalTo(itemId));
        assertThat(item.getOwner(), notNullValue());
        assertThat(item.getOwner(), equalTo(user));
        assertThat(item.getName(), equalTo(itemDto.getName()));
        assertThat(item.getDescription(), equalTo(itemDto.getDescription()));
        assertThat(item.getIsAvailable(), equalTo(itemDto.getIsAvailable()));

        UserDto userTest = userService.getUser(ownerId);

        assertThat(userTest.getName(), equalTo(userDto.getName()));
        assertThat(userTest.getEmail(), equalTo(userDto.getEmail()));
        assertThat(item.getRequest(), notNullValue());
        assertThat(item.getRequest().getId(), equalTo(1));
        assertThat(item.getRequest().getDescription(), equalTo(itemRequest.getDescription()));
        assertThat(item.getRequest().getRequestor().getId(), equalTo(requesterId));
    }

}