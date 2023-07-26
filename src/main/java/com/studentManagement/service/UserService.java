package com.studentManagement.service;

import com.studentManagement.model.PODUserInfoResult;
import com.studentManagement.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserBySsoId(Long ssoId);

    void saveOrUpdateUSer(User user, PODUserInfoResult userInfo);

    PODUserInfoResult getUserInfo(String accessToken);

    void deleteUser(User user);

    void makeAdmin(Long ssoId);
}
