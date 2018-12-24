package com.zhsl.convertor.model2vo;

import com.zhsl.model.OauthClientDetails;
import com.zhsl.util.BeanUtils;
import com.zhsl.vo.OauthClientDetailsVO;

public class OauthClientDetails2VO {
    public static OauthClientDetailsVO convert(OauthClientDetails oauthClientDetails, String rawSecret) {
        OauthClientDetailsVO oauthClientDetailsVO = new OauthClientDetailsVO();
        BeanUtils.copyProperties(oauthClientDetails, oauthClientDetailsVO);
        if (rawSecret != null && !"".equals(rawSecret)) {
            oauthClientDetailsVO.setClientSecret(rawSecret);
        }
        return oauthClientDetailsVO;
    }
}
