package ru.practicum.shareit.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.model.RequestDto;
import ru.practicum.shareit.request.repository.MemoryRequest;
import ru.practicum.shareit.user.model.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static ru.practicum.shareit.request.model.RequestMapper.itemRequestFromDto;

@ExtendWith(MockitoExtension.class)
public class ItemRequestRepositoryTest {

    private final User user = new User(1, "user@email.com", "User");
    private RequestDto requestDto = new RequestDto(
            1,
            "Test description",
            null,
            Collections.emptyList()
    );
    private final Request itemRequest = itemRequestFromDto(requestDto, user);
    private final Request itemRequest2 = itemRequestFromDto(requestDto, user);


    @Test
    public void findByRequesterIdOrderByCreatedDescTest() {
        MemoryRequest memoryRequest = mock(MemoryRequest.class);
        int userId = 1;
        List<Request> itemRequests = List.of(itemRequest, itemRequest2);

        Mockito.when(memoryRequest.findByRequestorIdOrderByCreatedDesc(eq(userId)))
                .thenReturn(itemRequests);

        List<Request> result = memoryRequest.findByRequestorIdOrderByCreatedDesc(userId);

        assertEquals(2, result.size());
        verify(memoryRequest).findByRequestorIdOrderByCreatedDesc(eq(userId));
    }

    @Test
    public void findByRequesterIdIsNotTest() {
        MemoryRequest memoryRequest = mock(MemoryRequest.class);

        int userId = 1;
        Pageable page = mock(Pageable.class);
        List<Request> itemRequests = List.of(itemRequest, itemRequest2);

        Mockito.when(memoryRequest.findByRequestorIdNotOrderByCreatedDesc(eq(userId), any(Pageable.class)))
                .thenReturn(itemRequests);

        List<Request> result = memoryRequest.findByRequestorIdNotOrderByCreatedDesc(userId, page);

        assertEquals(2, result.size());
        verify(memoryRequest).findByRequestorIdNotOrderByCreatedDesc(eq(userId), eq(page));
    }

}