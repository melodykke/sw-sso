package com.zhsl.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhsl.convertor.dto2model.UserInfoDTO2;
import com.zhsl.convertor.model2vo.UserInfo2VO;
import com.zhsl.dto.UserInfoDTO;
import com.zhsl.enums.SysEnum;
import com.zhsl.exception.SysException;
import com.zhsl.mapper.SysRoleMapper;
import com.zhsl.mapper.SysUserRoleMapper;
import com.zhsl.mapper.UserInfoMapper;
import com.zhsl.model.SysRole;
import com.zhsl.model.UserInfo;
import com.zhsl.service.UserService;
import com.zhsl.util.UUIDUtils;
import com.zhsl.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("用户名为：" + username + "的用户正在尝试登陆...");

        // 根据用户名查找用户信息
        UserInfo userInfo = userInfoMapper.findOneWithRolesAndPrivilegesByUsernameOrId(username, null);

        if (userInfo == null) {
            throw new UsernameNotFoundException("不存在此用户！");
        }

        return userInfo;
    }

    @Override
    public UserInfoVO findById(String id) {

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);

        UserInfoVO userInfoVO = UserInfo2VO.convert(userInfo);

        return userInfoVO;
    }

    @Override
    public UserInfoVO findByUsername(String username) {
        UserInfo userInfo = userInfoMapper.findByUsername(username);

        UserInfoVO userInfoVO = UserInfo2VO.convert(userInfo);

        return userInfoVO;
    }

    @Override
    public int create(UserInfoDTO userInfoDTO) {

        if (!userInfoDTO.getPassword().equals(userInfoDTO.getRePassword())) {
            log.error("【用户】 创建用户时，密码和确认密码不一致");
            throw new SysException(SysEnum.INCONSISTENT_PASSWORD_ERROR);
        }

        List<String> allUsername = userInfoMapper.findAllUsername();
        if (allUsername.contains(userInfoDTO.getUsername())) {
            log.error("【用户】 创建用户时，已存在拟申请用户名");
            throw new SysException(SysEnum.DUPLICATED_RECORD);
        }

        UserInfo userInfo = UserInfoDTO2.convert(userInfoDTO);
        // 将密码encode
        userInfo.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        // 插入数据库
        int result = userInfoMapper.insert(userInfo);

        return result;
    }

    @Override
    public int delete(String userId) {
        int result = userInfoMapper.deleteByPrimaryKey(userId);
        return result;
    }

    @Override
    public int modify(UserInfoDTO userInfoDTO) {
        return 0;
    }

    @Override
    public UserInfoVO findUserInfoByUsernameOrIdWithRolesAndPrivileges(String usernameOrId) {
        UserInfo userInfo = null;
        boolean foundOne = false;

        if (usernameOrId == null || "".equals(usernameOrId)) {
            log.error("【用户】 查询用户信息时， 没有收到正确的用户名或用户ID");
            throw new SysException(SysEnum.INVALID_INFO_RECEIVED_ERROR);
        }

        userInfo = userInfoMapper.findOneWithRolesAndPrivilegesByUsernameOrId(usernameOrId, null);

        if (userInfo != null) {
            foundOne = true;
        } else {
            userInfo = userInfoMapper.findOneWithRolesAndPrivilegesByUsernameOrId(null, usernameOrId);
            if (userInfo != null) {
                foundOne = true;
            }
        }

        if (foundOne == true) {
            UserInfoVO userInfoVO = UserInfo2VO.convert(userInfo);
            return userInfoVO;
        }

        return null;
    }

    @Override
    public List<UserInfoVO> findAll() {
        return null;
    }

    @Override
    public List<SysRole> findAllRoles() {
        return null;
    }


}
