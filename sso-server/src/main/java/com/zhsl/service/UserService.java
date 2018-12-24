package com.zhsl.service;

import com.zhsl.dto.UserInfoDTO;
import com.zhsl.model.SysRole;
import com.zhsl.vo.UserInfoVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    /**
     * 查询用户 根据用户ID查询 只是用户的信息
     * @param id
     * @return
     */
    UserInfoVO findById(String id);

    /**
     * 查询用户 根据用户username查询
     * @param id
     * @return
     */
    UserInfoVO findByUsername(String username);

    /**
     * 创建用户
     * @param userInfoDTO
     * @return
     */
    int create(UserInfoDTO userInfoDTO);

    /**
     * 删除用户
     * @param userInfoDTO
     * @return
     */
    int delete(String userId);

    /**
     * 修改用户
     * @param userInfoDTO
     * @return
     */
    int modify(UserInfoDTO userInfoDTO);

    /**
     * 通过用户名或ID查找用户包括用户的角色和权限信息
     * @param usernameOrId
     * @return
     */
    UserInfoVO findUserInfoByUsernameOrIdWithRolesAndPrivileges(String usernameOrId);

    /**
     * 查询所有的用户 （不包含最上级用户）
     * @return
     */
    List<UserInfoVO> findAll();

    /**
     * 获取系统所有角色 （排除工程管理部和建管处角色）
     * 只有最高级用户才能访问此方法
     * @return
     */
    List<SysRole> findAllRoles();

}
