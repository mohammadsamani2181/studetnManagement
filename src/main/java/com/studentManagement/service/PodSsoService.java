package com.studentManagement.service;

import com.studentManagement.model.PODUserInfoResult;
import com.studentManagement.model.PODUserTokenInfo;

public interface PodSsoService {
    PODUserTokenInfo getUserToken(String refreshToken, String code);

    PODUserTokenInfo getUserToken(String code);

//    PODUserInfo getPodUser(String accessToken);

    PODUserInfoResult getPodUser(String accessToken);

    String getLoginAddress();
}
