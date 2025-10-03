package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Вещь.
 */
@Data
public class Item {
    private Long id;
    private String name;
    private String description;
    private boolean available;
    private Long ownerId;
}
