package com.studentManagement.service;

import com.studentManagement.model.PODUser_Info;
import com.studentManagement.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserBySsoId(Long ssoId);
    void saveOrUpdateUSer(User user, PODUser_Info userInfo);
}
