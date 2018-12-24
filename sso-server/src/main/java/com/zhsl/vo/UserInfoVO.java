package com.zhsl.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhsl.model.SysRole;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoVO {
    private String userId;

    private Date createTime;

    private String name;

    private String password;

    private Date updateTime;

    private String username;

    private List<SysRole> roles;

    private boolean accountNonLocked;
}
