package com.boot.pro.mapper;

import java.util.List;

import com.boot.pro.bean.ProBean;
import com.boot.common.base.BaseMapper;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProMapper extends BaseMapper<ProBean> {


    @Cacheable(value = "basPro", key = "'getBasProList_'+#p0.getPro_name()+#p0.getPro_code()", unless="#result == null")
    //@Cacheable(value = "basPro",keyGenerator = "keyGenerator")
    List<ProBean> getBasProList(ProBean bean);

}
