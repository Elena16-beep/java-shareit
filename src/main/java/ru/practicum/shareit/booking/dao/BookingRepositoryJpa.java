package ru.practicum.shareit.booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepositoryJpa extends JpaRepository<Booking, Long> {

    List<Booking> findAllByItemOwnerId(Long id);

    List<Booking> findAllByBooker(User booker);

    boolean existsByItemIdAndBookerIdAndEndBefore(Long itemId, Long bookerId, LocalDateTime date);

    List<Booking> findAllByItem(Item item);
}
