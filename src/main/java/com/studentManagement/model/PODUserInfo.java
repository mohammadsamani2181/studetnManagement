package com.studentManagement.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PODUserInfo {
    @SerializedName("hasError")
    private boolean hasError;

    @SerializedName("result")
    private PODUserInfoResult result;
}
