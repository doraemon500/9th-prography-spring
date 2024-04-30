package com.example.prography_quest.domain.user.service;

import com.example.prography_quest.domain.user.domain.User;
import com.example.prography_quest.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public Page<User> findUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findAllByOrderById(pageRequest);
    }

    public User findUser(int id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }
}
