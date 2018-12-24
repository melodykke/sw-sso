package com.zhsl.mapper;

import com.zhsl.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
    int deleteByPrimaryKey(String userId);

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(String userId);

    List<UserInfo> selectAll();

    int updateByPrimaryKey(UserInfo record);

    UserInfo findByUsername(String username);

    /**
     * 通过 用户名 或者ID 查找用户、用户的角色及权限信息
     * @param username
     * @param userId
     * @return
     */
    UserInfo findOneWithRolesAndPrivilegesByUsernameOrId(@Param("username") String username, @Param("userId") String userId);

    /**
     * 列出所有用户名 主要是防止注册用户名重复用
     * @return
     */
    List<String> findAllUsername();
}