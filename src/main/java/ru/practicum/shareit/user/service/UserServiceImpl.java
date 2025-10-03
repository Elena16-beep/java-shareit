package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto add(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        user = userRepository.save(user, false);

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto update(Long userId, UserDto userDto) {
        boolean isSameEmail = false;
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));

        if (userDto.getName() != null) {
            existingUser.setName(userDto.getName());
        }

        if (userDto.getEmail() != null) {
            isSameEmail = existingUser.getEmail().equals(userDto.getEmail());
            existingUser.setEmail(userDto.getEmail());
        }

        existingUser = userRepository.save(existingUser, isSameEmail);

        return UserMapper.mapToUserDto(existingUser);
    }

    @Override
    public UserDto getById(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + id + " не найден"));

        return UserMapper.mapToUserDto(existingUser);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
