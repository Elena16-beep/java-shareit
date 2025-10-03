package ru.practicum.shareit.user.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.InternalServerException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user, Boolean isSameEmail) {
//        validate(user);
        System.out.println("save userDto " + user);
        System.out.println("save isSameEmail " + isSameEmail);

        if (user.getId() == null) {
            if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
                throw new InternalServerException("s Пользователь с email " + user.getEmail() + " уже существует");
            }

            user.setId(getNextId());
        }

//        User newUser = users.get(user.getId());
//        if (newUser != null && user.getEmail() != null && !user.getEmail().equals(newUser.getEmail())) {
//            validate(user);
//        }

//        System.out.println("users " + users);

        User newUser = new User();

        if (user.getId() != null) {
            System.out.println("users " + users);
            newUser = users.get(user.getId());
            System.out.println("newUser " + newUser);

            if (user.getEmail() != null && !isSameEmail) {
                if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail())
                && !u.getId().equals(user.getId()))) {
                    throw new InternalServerException("u Пользователь с email " + user.getEmail() + " уже существует");
                }
            }
        }

//        User user = UserMapper.mapToUser(userDto);
//        System.out.println("return user " + user);

//        if (userDto.getName() != null) {
//            user.setName(userDto.getName());
//        }
//
//        if (userDto.getEmail() != null) {
//            user.setEmail(userDto.getEmail());
//        }

//        if (user.getId() == null) {
////            if (users.values().stream().anyMatch(u -> u.getEmail().equals(userDto.getEmail()))) {
////                throw new InternalServerException("s Пользователь с email " + userDto.getEmail() + " уже существует");
////            }
//
//            user.setId(getNextId());
//        }
        System.out.println("return1 user " + user);
        System.out.println("return1 users " + users);

        users.put(user.getId(), user);


        System.out.println("return2 user " + user);
        System.out.println("return2 users " + users);

//        System.out.println("users " + users);

        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public void delete(Long id) {
        findById(id).orElseThrow(() -> new NotFoundException("Пользователь с id =" + id + " не найден"));
        users.remove(id);
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);

        return ++currentMaxId;
    }

    private void validate(User newUser) {
        if (users.values().stream().anyMatch(user -> newUser.getEmail().equals(user.getEmail()))) {
            throw new InternalServerException("Пользователь с email = " + newUser.getEmail() + " уже существует");
        }
    }
}
