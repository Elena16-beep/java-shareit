package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(getNextId());
        }

        items.put(item.getId(), item);

        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public List<Item> findByOwnerId(Long ownerId) {
        List<Item> itemList = new ArrayList<>();
        for (Item item : items.values()) {
            if (Objects.equals(item.getOwnerId(), ownerId)) {
                itemList.add(item);
            }
        }

        return itemList;
    }

    @Override
    public List<Item> search(String text) {
        List<Item> itemList = new ArrayList<>();
        String lowerText = text.toLowerCase();
        for (Item item : items.values()) {
            if (item.isAvailable() && (item.getName().toLowerCase().contains(lowerText)
                || item.getDescription().toLowerCase().contains(lowerText))) {
                itemList.add(item);
            }
        }

        return itemList;
    }

    private long getNextId() {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);

        return ++currentMaxId;
    }
}
