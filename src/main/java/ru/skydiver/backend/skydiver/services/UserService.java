package ru.skydiver.backend.skydiver.services;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.skydiver.backend.skydiver.model.UserEntity;
import ru.skydiver.backend.skydiver.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void banUser(String userName) {
        userRepository.loadByUserName(userName).ifPresent(user ->
                userRepository.banUser(user.getName()));
    }

    public List<UserEntity> getUserList() {
        return userRepository.getUserList();
    }
}
