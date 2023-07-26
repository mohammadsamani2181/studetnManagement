package com.studentManagement.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PODUserInfoResult {
    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("username")
    private String username;

    @SerializedName("ssoId")
    private String ssoId;

    @SerializedName("email")
    private String email;
}
