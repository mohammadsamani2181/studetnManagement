package com.studentManagement.service.impl;

import com.studentManagement.config.ApplicationConfig;
import com.studentManagement.exception.InternalServerException;
import com.studentManagement.log.ExceptionLogObject;
import com.studentManagement.log.LogObject;
import com.studentManagement.log.LogUtil;
import com.studentManagement.log.Subject;
import com.studentManagement.model.DTO.request.AuthenticationDTORequest;
import com.studentManagement.model.DTO.response.AuthenticationDTOResponse;
import com.studentManagement.model.PODUserInfoResult;
import com.studentManagement.model.PODUserTokenInfo;
import com.studentManagement.model.PodToken;
import com.studentManagement.model.User;
import com.studentManagement.repository.UserRepository;
import com.studentManagement.service.AuthenticationService;
import com.studentManagement.service.JwtService;
import com.studentManagement.service.PodSsoService;
import com.studentManagement.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ApplicationConfig applicationConfig;
    private final PodSsoService podSsoService;

    @Override
    public AuthenticationDTOResponse authenticate(AuthenticationDTORequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user with the given email!!");
        }

        String jwt = jwtService.generateToken(user.get());

        return AuthenticationDTOResponse.builder()
                .token(jwt)
                .build();
    }

    @Override
    public User get(AuthenticationDTORequest request) {
        return userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no user"));
    }

    @Override
    public void authorizePODSSO(String code) throws InternalServerException {

        /* use a normal way to call api*/
//        PODUserTokenInfo podUserTokenInfo = podSsoService.getUserToken(null, code);


        /* use retrofit*/
        PODUserTokenInfo podUserTokenInfo = podSsoService.getUserToken(code);

        System.out.println(podUserTokenInfo.getAccessToken());

        if (podUserTokenInfo != null) {

            /* use retrofit, but you can find normal way with the same name */
            PODUserInfoResult userInfo = podSsoService.getPodUser(podUserTokenInfo.getAccessToken());

            if (userInfo != null) {
                Optional<User> user = userService.getUserBySsoId(Long.parseLong(userInfo.getSsoId()));

                if (user.isEmpty()) {

                    userService.saveOrUpdateUSer(null, userInfo);

                }
            }
        } else {
            throw new InternalServerException("An Error occurred during getting User token from POD Service");
        }

    }

    @Override
    public void getPodLoginAddress(HttpServletResponse response) {
        LogUtil.info(log, "User wants to get pod login address", Subject.AUTH);
        String url = podSsoService.getLoginAddress();

        try {
            response.sendRedirect(url);
            LogUtil.info(log, "User redirected to pod login page", Subject.AUTH);

        } catch (IOException e) {
            LogObject exceptionLog = ExceptionLogObject.builder()
                    .message("An Error occurred during getting pod login address")
                    .information(e)
                    .build();
            LogUtil.error(log, exceptionLog);

            throw new InternalServerException("Get Login Address With Pod SSO!");
        }
    }

    @Override
    public PodToken getNewAccessToken(String refreshToken) {
        PODUserTokenInfo token = podSsoService.getUserToken(refreshToken, null);
        return convertPODUserTokenInfoToPodToken(token);
    }

    private PodToken convertPODUserTokenInfoToPodToken(PODUserTokenInfo podUserTokenInfo) {
        return PodToken.builder()
                .accessToken(podUserTokenInfo.getAccessToken())
                .refreshToken(podUserTokenInfo.getRefreshToken())
                .scope(podUserTokenInfo.getScope())
                .expiresIn(podUserTokenInfo.getExpiresIn())
                .build();
    }
}
