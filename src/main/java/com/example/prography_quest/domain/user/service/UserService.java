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
//  page:
//        •	조회할 페이지 번호를 지정합니다. (0부터 시작)
//        •	예: page = 0은 첫 번째 페이지를 의미.
//	size:
//        •	한 페이지에 포함할 데이터 수를 지정합니다.
//  한 페이지당 포함된 요소의 개수는 PageRequest.of(page, size)에서 지정한 size 값으로 결정됩니다.
//  특정 페이지의 실제 포함된 요소 수는 page.getNumberOfElements()를 통해 확인할 수 있습니다.

    public User findUser(int id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }
}
