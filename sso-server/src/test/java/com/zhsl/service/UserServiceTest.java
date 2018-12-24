package com.zhsl.service;

import com.zhsl.mapper.UserInfoMapper;
import com.zhsl.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void findUserInfoByUsernameOrIdWithRolesAndPrivileges() throws Exception {
        UserInfo userInfo = userInfoMapper.findOneWithRolesAndPrivilegesByUsernameOrId("melodykke", null);
        System.out.println(userInfo);
    }

}
