package com.zhsl.convertor.dto2model;

import com.zhsl.dto.UserInfoDTO;
import com.zhsl.model.UserInfo;
import com.zhsl.util.UUIDUtils;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class UserInfoDTO2 {

    public static UserInfo convert(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        userInfo.setUserId(UUIDUtils.getUUIDString());
        userInfo.setUpdateTime(new Date());
        return userInfo;
    }

}
