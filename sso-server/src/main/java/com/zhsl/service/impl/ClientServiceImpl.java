package com.zhsl.service.impl;

import com.zhsl.convertor.dto2model.OauthClientDetailsDTO2;
import com.zhsl.convertor.model2vo.OauthClientDetails2VO;
import com.zhsl.dto.OauthClientDetailsDTO;
import com.zhsl.enums.SysEnum;
import com.zhsl.exception.SysException;
import com.zhsl.mapper.OauthClientDetailsMapper;
import com.zhsl.model.OauthClientDetails;
import com.zhsl.model.UserInfo;
import com.zhsl.service.ClientService;
import com.zhsl.vo.OauthClientDetailsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public OauthClientDetailsVO addClient(OauthClientDetailsDTO oauthClientDetailsDTO) {

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        OauthClientDetails clientDetails = OauthClientDetailsDTO2.convert(oauthClientDetailsDTO);
        // 设置外键
        clientDetails.setUserId(userInfo.getUserId());
        // 将上一步自动生成的客户端密码encode
        String rawSecret = clientDetails.getClientSecret();
        clientDetails.setClientSecret(passwordEncoder.encode(rawSecret));
        // 存入数据库
        int result = oauthClientDetailsMapper.insert(clientDetails);
        if (result != 1) {
            log.error("【客户端】 创建失败！");
            throw new SysException(SysEnum.SYS_INNER_ERROR);
        }
        // 向前端返回带rawsecret真实客户端密码，并要求其记住。
        return OauthClientDetails2VO.convert(clientDetails, rawSecret);
    }

    @Override
    public int removeClient(int id) {
        return 0;
    }

    @Override
    public List<OauthClientDetailsVO> listAll() {
        List<OauthClientDetails> oauthClientDetailsList = oauthClientDetailsMapper.selectAll();
        List<OauthClientDetailsVO> oauthClientDetailsVOs = oauthClientDetailsList.stream()
                .map(e -> OauthClientDetails2VO.convert(e, null)).collect(Collectors.toList());
        return oauthClientDetailsVOs;
    }

}
