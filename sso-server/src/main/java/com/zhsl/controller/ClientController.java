package com.zhsl.controller;

import com.zhsl.dto.OauthClientDetailsDTO;
import com.zhsl.enums.SysEnum;
import com.zhsl.exception.SysException;
import com.zhsl.service.ClientService;
import com.zhsl.util.ResultUtil;
import com.zhsl.vo.support.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResultVO create(@Valid @RequestBody OauthClientDetailsDTO oauthClientDetailsDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("【客户端】 新增用户失败！收到的clientInfoDTO:{}; 错误：{}", oauthClientDetailsDTO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.INVALID_INFO_RECEIVED_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        return ResultUtil.success(clientService.addClient(oauthClientDetailsDTO));
    }
}
