package com.zhsl.convertor.dto2model;

import com.zhsl.dto.OauthClientDetailsDTO;
import com.zhsl.model.OauthClientDetails;
import com.zhsl.util.BeanUtils;
import com.zhsl.util.UUIDUtils;

/**
 * 将接受到的客户端注册信息封装
 */
public class OauthClientDetailsDTO2 {
    public static OauthClientDetails convert(OauthClientDetailsDTO oauthClientDetailsDTO) {
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        BeanUtils.copyProperties(oauthClientDetailsDTO, oauthClientDetails);
        oauthClientDetails.setClientId(UUIDUtils.getUUIDString().toString());
        oauthClientDetails.setClientSecret(UUIDUtils.getUUIDString().toString());
        oauthClientDetails.setScope("basic_user_info");
        oauthClientDetails.setAuthorizedGrantTypes("authorization_code");
        oauthClientDetails.setAccessTokenValidity(7200);
        oauthClientDetails.setRefreshTokenValidity(2592000);
        return oauthClientDetails;
    }
}
