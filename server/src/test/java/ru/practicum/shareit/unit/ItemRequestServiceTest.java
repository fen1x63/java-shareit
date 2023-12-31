package ru.practicum.shareit.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.exception.model.NotFoundUserException;
import ru.practicum.shareit.item.repository.MemoryItem;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.model.RequestDto;
import ru.practicum.shareit.request.repository.MemoryRequest;
import ru.practicum.shareit.request.service.RequestServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.MemoryUser;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static ru.practicum.shareit.request.model.RequestMapper.itemRequestFromDto;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceTest {
    @Mock
    private MemoryRequest memoryRequest;
    @Mock
    private MemoryUser memoryUser;
    @Mock
    private MemoryItem memoryItem;
    @InjectMocks
    private RequestServiceImpl requestService;

    private User user;
    private RequestDto requestDto;

    @BeforeEach
    public void setUp() {
        user = new User(1, "user1", "user1@email.com");

        requestDto = new RequestDto(
                1,
                "Test description",
                null,
                Collections.emptyList()
        );
    }

    @Test
    public void shouldAddItemRequest() {
        Mockito
                .when(memoryUser.findById(anyInt()))
                .thenReturn(Optional.of(user));
        Mockito
                .when(memoryRequest.save(any(Request.class)))
                .then(returnsFirstArg());

        RequestDto itemRequestDtoOutgoing = requestService.addItemRequest(1, requestDto);

        assertThat(itemRequestDtoOutgoing.getId(), equalTo(requestDto.getId()));
        assertThat(itemRequestDtoOutgoing.getDescription(), equalTo(requestDto.getDescription()));
        assertThat(itemRequestDtoOutgoing.getItems(), equalTo(requestDto.getItems()));
    }

    @Test
    public void shouldNotAddItemRequestWhenUserNotFound() {
        Mockito
                .when(memoryUser.findById(anyInt()))
                .thenReturn(Optional.empty());

        NotFoundUserException e = Assertions.assertThrows(
                NotFoundUserException.class,
                () -> requestService.addItemRequest(1, requestDto)
        );

        assertThat(e.getMessage(), equalTo("Not found userId: 1"));
    }

    @Test
    public void shouldNotGetItemRequestsByUserIdWhenUserNotFound() {
        Mockito
                .when(memoryUser.existsById(anyInt()))
                .thenReturn(false);

        NotFoundUserException e = Assertions.assertThrows(
                NotFoundUserException.class,
                () -> requestService.getItemRequestsByUserId(1)
        );

        assertThat(e.getMessage(), equalTo("Not found userId: 1"));
    }

    @Test
    public void shouldNotGetAllItemRequestsWhenUserNotFound() {
        Mockito
                .when(memoryUser.existsById(anyInt()))
                .thenReturn(false);

        NotFoundUserException e = Assertions.assertThrows(
                NotFoundUserException.class,
                () -> requestService.getAllItemRequests(1, 0, 5)
        );

        assertThat(e.getMessage(), equalTo("Not found userId: 1"));
    }

    @Test
    public void shouldGetItemRequestById() {
        Mockito
                .when(memoryUser.existsById(anyInt()))
                .thenReturn(true);
        Mockito
                .when(memoryRequest.findById(anyInt()))
                .thenReturn(
                        Optional.of(itemRequestFromDto(requestDto, user))
                );
        Mockito
                .when(memoryItem.findByRequestId(anyInt()))
                .thenReturn(Collections.emptyList());

        RequestDto itemRequestDtoOutgoing = requestService.getItemRequestById(1, 1);

        assertThat(itemRequestDtoOutgoing.getId(), equalTo(requestDto.getId()));
        assertThat(itemRequestDtoOutgoing.getDescription(), equalTo(requestDto.getDescription()));
        assertThat(itemRequestDtoOutgoing.getItems(), equalTo(requestDto.getItems()));
    }

    @Test
    public void shouldNotGetItemRequestByIdWhenUserNotFound() {
        Mockito
                .when(memoryUser.existsById(anyInt()))
                .thenReturn(false);

        NotFoundUserException e = Assertions.assertThrows(
                NotFoundUserException.class,
                () -> requestService.getItemRequestById(1, 1)
        );

        assertThat(e.getMessage(), equalTo("Not found userId: 1"));
    }

    @Test
    public void shouldNotGetItemRequestByIdWhenItemRequestNotFound() {
        Mockito
                .when(memoryUser.existsById(anyInt()))
                .thenReturn(true);
        Mockito
                .when(memoryRequest.findById(anyInt()))
                .thenReturn(Optional.empty());

        NotFoundException e = Assertions.assertThrows(
                NotFoundException.class,
                () -> requestService.getItemRequestById(1, 1)
        );

        assertThat(e.getMessage(), equalTo("Request requestId: 1"));
    }
}