package com.zhsl.controller;

import com.zhsl.dto.UserInfoDTO;
import com.zhsl.enums.SysEnum;
import com.zhsl.exception.SysException;
import com.zhsl.model.UserInfo;
import com.zhsl.properties.SecurityProperties;
import com.zhsl.service.UserService;
import com.zhsl.util.ResultUtil;
import com.zhsl.vo.UserInfoVO;
import com.zhsl.vo.support.ResultVO;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(allowCredentials="true",maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 将jwt(String)的payload部分读出来组装成一个springsecurity的authentication
     * @param user
     * @param request
     * @return
     */
    @GetMapping("/basicUserInfo")
    public Object getBasicUserInfo(Authentication user, HttpServletRequest request) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
        String token = StringUtils.substringAfter(request.getHeader("Authorization"), "Bearer ");

		Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
					.parseClaimsJws(token).getBody();

		String company = (String) claims.get("company");

		String username = (String) user.getPrincipal();
        if (username == null || "".equals(username)) {
            log.error("【SSO用户基本信息】 获取用户基本信息失败！ 没有获取到用户名！");
            throw new SysException(SysEnum.SYS_INNER_ERROR);
        }
        UserInfoVO userInfoVO = userService.findUserInfoByUsernameOrIdWithRolesAndPrivileges(username);
        Map<String, Object> basic_user_info = new HashMap<>();
        basic_user_info.put("user_id", userInfoVO.getUserId());
        basic_user_info.put("username", username);
        basic_user_info.put("company", company);
        basic_user_info.put("authorities", user.getAuthorities());
        return basic_user_info;
    }

    @PostMapping
    public ResultVO create(@Valid @RequestBody UserInfoDTO userInfoDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("【用户】 用户创建失败！ 收到信息校验失败！ bindingResult：{}",
                    bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.VERIFY_RECEIVED_INFO_ERROR.getCode(), bindingResult.getFieldError()
                    .getDefaultMessage());
        }

        int result = userService.create(userInfoDTO);

        if (result == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.failed();
        }
    }

    @DeleteMapping
    public ResultVO delete(@PathVariable String id) {

        int result = userService.delete(id);

        if (result == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.failed();
        }
    }



}
