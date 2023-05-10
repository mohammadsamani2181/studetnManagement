package com.studentManagement.service.impl;

import com.studentManagement.config.ApplicationConfig;
import com.studentManagement.model.PODUser_Info;
import com.studentManagement.model.User;
import com.studentManagement.repository.UserRepository;
import com.studentManagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ApplicationConfig applicationConfig;

    public UserServiceImpl(UserRepository userRepository, ApplicationConfig applicationConfig) {
        this.userRepository = userRepository;
        this.applicationConfig = applicationConfig;
    }

    @Override
    public Optional<User> getUserBySsoId(Long ssoId) {
        return userRepository.findBySsoId(ssoId);
    }

    @Override
    public void saveOrUpdateUSer(User user, PODUser_Info userInfo) {
        if (user == null) {
            User newUser = User.builder()
                    .firstName(userInfo.getFirstName())
                    .lastName(userInfo.getLastName())
                    .email(userInfo.getEmail())
                    .ssoId(Long.parseLong(userInfo.getSsoId()))
                    .build();

            userRepository.saveAndFlush(newUser);
        }else {
            user.setFirstName(userInfo.getFirstName());
            user.setLastName(userInfo.getLastName());
            user.setEmail(userInfo.getEmail());

            userRepository.saveAndFlush(user);
        }
    }
}
