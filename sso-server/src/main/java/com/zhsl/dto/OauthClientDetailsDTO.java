package com.zhsl.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class OauthClientDetailsDTO {
    private String clientId;

    @NotBlank(message = "客户端名称不能为空！")
    private String clientName;

    private String clientDesc;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    @NotBlank(message = "回调地址不能为空！")
    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;

}