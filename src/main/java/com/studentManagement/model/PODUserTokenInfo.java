package com.studentManagement.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PODUserTokenInfo {
    @SerializedName("state")
    private String state;

    @SerializedName("code")
    private String code;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private String expiresIn;

    @SerializedName("scope")
    private String scope;

    @ToString.Exclude
    @SerializedName("access_token")
    private String accessToken;

    @ToString.Exclude
    @SerializedName("refresh_token")
    private String refreshToken;

    @ToString.Exclude
    @SerializedName("id_token")
    private String idToken;

    @ToString.Exclude
    @SerializedName("device_uid")
    private String deviceUid;
}
