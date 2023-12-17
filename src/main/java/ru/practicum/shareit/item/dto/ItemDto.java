package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.model.BookingDtoDefault;
import ru.practicum.shareit.booking.model.BookingDtoOutgoing;
import ru.practicum.shareit.item.model.comment.CommentDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class ItemDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private BookingDtoDefault lastBooking;
    private BookingDtoOutgoing nextBooking;
    private List<CommentDto> comments;
    private Long requestId;
}
