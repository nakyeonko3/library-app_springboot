package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.user.UserCreateRequest;
import com.group.libraryapp.dto.user.request.user.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceV1 {

    private final UserJdbcRepository userRepository;

    public UserServiceV1(UserJdbcRepository userJdbcRepository) {
        this.userRepository = userJdbcRepository;
    }

    public void saveUser(UserCreateRequest request){
        userRepository.saveUser(request.getAge(),request.getName());
    }

    public List<UserResponse> getUsers(){
        return userRepository.getUsers();
    }

    public void updateUser(UserUpdateRequest request) {
        if (userRepository.isUserNotExist(request.getId())) {
            throw new IllegalArgumentException();
        }
        userRepository.updateUserName(request.getName(), request.getId());
    }

    public void deleteUser(String name) {
        if (userRepository.isUserNotExist(name)) {
            throw new IllegalArgumentException();
        }
        userRepository.deleteUserName(name);
    }
}
