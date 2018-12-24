package com.zhsl.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;


@Data
public class UserInfoDTO {

    private String userId;

    private boolean accountNonLocked;

    @NotBlank(message = "用户姓名不能为空！")
    @Size(min = 4, max = 32, message = "水库名必须由4至32个汉字组成")
    private String name;

    @NotBlank(message = "密码不能为空！")
    @Size(min = 4, max = 32, message = "密码必须由4至32个数字、字母或特殊字符组成")
    private String password;

    @NotBlank(message = "确认密码不能为空！")
    @Size(min = 4, max = 32, message = "密码必须由4至32个数字、字母或特殊字符组成")
    private String rePassword;

    @NotBlank(message = "账号名不能为空！")
    @Size(min = 4, max = 32, message = "账号必须由4至32个字母组成")
    private String username;
}
