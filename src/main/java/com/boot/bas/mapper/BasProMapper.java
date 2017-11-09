package com.boot.bas.mapper;

import com.boot.bas.bean.BasProBean;
import com.boot.util.base.BaseMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasProMapper extends BaseMapper<BasProBean> {


    @Cacheable(value = "basPro", key = "'getBasProList_'+#p0.getPro_name()+#p0.getPro_code()", unless="#result == null")
    //@Cacheable(value = "basPro",keyGenerator = "keyGenerator")
    List<BasProBean> getBasProList(BasProBean bean);

}
