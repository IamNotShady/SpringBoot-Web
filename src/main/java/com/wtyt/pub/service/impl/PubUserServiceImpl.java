package com.wtyt.pub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wtyt.pub.bean.PubUserBean;
import com.wtyt.pub.mapper.PubUserMapper;
import com.wtyt.pub.service.PubUserService;
import com.wtyt.util.base.BaseException;

@Service
public class PubUserServiceImpl implements PubUserService {

    @Autowired
    private PubUserMapper pubUserMapper;

    @Override
    public PubUserBean getUserByName(PubUserBean pubUserBean)
            throws BaseException, Exception {
        PubUserBean bean = pubUserMapper.getUserByName(pubUserBean
                .getLogin_name());
        if (bean == null) {
            throw new BaseException("用户在系统中不存在");
        }
        if (!pubUserBean.getPass_word().equals(bean.getPass_word())) {
            throw new BaseException("密码不正确");
        }
        return bean;
    }

}
