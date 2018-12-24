package com.zhsl.service;

import com.zhsl.dto.OauthClientDetailsDTO;
import com.zhsl.vo.OauthClientDetailsVO;

import java.util.List;

public interface ClientService {

    /**
     * 注册客户端
     * @param oauthClientDetailsDTO
     * @return 自增id
     */
    OauthClientDetailsVO addClient(OauthClientDetailsDTO oauthClientDetailsDTO);

    /**
     * 删除客户端
     */
    int removeClient(int id);

    /**
     * 罗列所有客户端
     */
    List<OauthClientDetailsVO> listAll();

}
