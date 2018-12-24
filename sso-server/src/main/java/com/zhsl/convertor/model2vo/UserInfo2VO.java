package com.zhsl.convertor.model2vo;

import com.zhsl.model.SysRole;
import com.zhsl.model.UserInfo;
import com.zhsl.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class UserInfo2VO {

    public static UserInfoVO convert(UserInfo userInfo) {
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo, userInfoVO);

        if (userInfo.getRoles() != null && userInfo.getRoles().size() > 0) {
            List<SysRole> sysRoles = userInfo.getRoles();
            userInfoVO.setRoles(sysRoles);
        }
        return userInfoVO;
    }

}
