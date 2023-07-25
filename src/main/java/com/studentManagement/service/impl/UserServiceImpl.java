package com.studentManagement.service.impl;

import com.studentManagement.config.ApplicationConfig;
import com.studentManagement.log.LogObject;
import com.studentManagement.log.LogUtil;
import com.studentManagement.log.UserLogObject;
import com.studentManagement.model.PODUserInfoResult;
import com.studentManagement.model.SchoolRole;
import com.studentManagement.model.SocketPrincipal;
import com.studentManagement.model.User;
import com.studentManagement.repository.UserRepository;
import com.studentManagement.service.PodSsoService;
import com.studentManagement.service.UserService;
import com.studentManagement.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ApplicationConfig applicationConfig;
    private PodSsoService podSsoService;
    @Value("${pod.admin.ssoId}")
    private String adminSsoId;

    public UserServiceImpl(UserRepository userRepository,
                           ApplicationConfig applicationConfig,
                           PodSsoService podSsoService) {

        this.userRepository = userRepository;
        this.applicationConfig = applicationConfig;
        this.podSsoService = podSsoService;
    }

    @Override
    public Optional<User> getUserBySsoId(Long ssoId) {
        return userRepository.findBySsoId(ssoId);
    }

    @Override
    public void saveOrUpdateUSer(User user, PODUserInfoResult userInfo) {
        if (user == null) {
            user = User.builder()
                    .firstName(userInfo.getFirstName())
                    .lastName(userInfo.getLastName())
                    .email(userInfo.getEmail())
                    .ssoId(Long.parseLong(userInfo.getSsoId()))
                    .build();

            setRolesForUser(user.getRoles(), user);

            userRepository.saveAndFlush(user);
        } else {
            user.setFirstName(userInfo.getFirstName());
            user.setLastName(userInfo.getLastName());
            user.setEmail(userInfo.getEmail());

            userRepository.saveAndFlush(user);
        }
    }

    @Override
    public PODUserInfoResult getUserInfo(String accessToken) {
        return podSsoService.getPodUser(accessToken);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void makeAdmin(Long ssoId) throws ResponseStatusException {
        SocketPrincipal socketPrincipal = (SocketPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> user = this.getUserBySsoId(ssoId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        ServiceUtils.makeAdmin(user.get());
        userRepository.saveAndFlush(user.get());


        LogObject userLogObject = UserLogObject.builder()
                .message("make admin")
                .firstUserSsoId(socketPrincipal.getName())
                .secondUserSsoId(String.valueOf(ssoId))
                .build();
        LogUtil.info(log, userLogObject);

    }


    private void setRolesForUser(Set<SchoolRole> roles, User user) {
        roles.add(SchoolRole.USER);

        if (user.getSsoId().toString().equals(adminSsoId)) {
            ServiceUtils.makeAdmin(user);
        }
    }


}
