package com.anypluspay.admin.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxj
 * 2025/5/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginResponse {

    /**
     * 访问令牌
     */
    private String accessToken;
}
