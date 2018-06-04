package com.github.boot.pro.service.impl;

import com.github.boot.common.aop.LoggerAnnotation;
import com.github.boot.pro.bean.ProBean;
import com.github.boot.pro.mapper.ProMapper;
import com.github.boot.pro.service.ProService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProServiceImpl implements ProService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProMapper basProMapper;

    @LoggerAnnotation(description = "BasProServiceImpl.queryBasPro")
    public void queryBasPro(ProBean bean) throws Exception {
        if (bean.getPage() != null && bean.getRows() != null) {
            PageHelper.startPage(bean.getPage(), bean.getRows());
        }
        List<ProBean> list = basProMapper.getBasProList(bean);
        PageInfo<ProBean> pageInfo = new PageInfo<ProBean>(list);
        bean.setProList(list);
    }

    public void insBasPro(ProBean bean) throws Exception {
        basProMapper.insertSelective(bean);
    }

    public void insBasProErr(ProBean bean) throws Exception {
        basProMapper.insert(bean);
    }

    @CacheEvict(value = "basPro", allEntries = true)
    public void delBasPro(ProBean bean) throws Exception {
        basProMapper.delete(bean);
    }
}
