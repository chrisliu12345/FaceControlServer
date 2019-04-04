package com.gd.service.common.impl;

import com.gd.config.DataSourceKey;
import com.gd.config.DynamicDataSourceContextHolder;
import com.gd.config.TargetDataSource;
import com.gd.dao.common.IResAttrDao;

import com.gd.domain.video.Camera1;
import com.gd.domain.video.Res_Attr;


import com.gd.service.common.IResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gd.config.DataSourceKey.ds_slave;

/**
 * Created by Administrator on 2017/9/18.
 */
@Service("resService")
public class ResServiceImpl implements IResService {
    public static final String CACHE_KEY = "'resInfo'";
    public static final String DEMO_CACHE_NAME = "demo";

    @Autowired
    private IResAttrDao iResAttr;



    /*@Cacheable(value = DEMO_CACHE_NAME, key = CACHE_KEY)//存入缓存*/
    @Override
    public List<Res_Attr> list() {
        return this.iResAttr.queryForObject();
    }

    @Override
    public Integer getRescount() {
        DynamicDataSourceContextHolder.setSlave();
        return this.iResAttr.getRescount();
    }

    @Override
    public String queryforobject2(Res_Attr res_attr) {
        return this.iResAttr.queryForObject2(res_attr);
    }

    @Override
    public List<String> deviceIDtoResID(String de) {
        return this.iResAttr.deviceIDtoResID(de);
    }

    @Override
    public String queryforDeviceID(Res_Attr res_attr) {
        return this.iResAttr.queryforDeviceID(res_attr);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = CACHE_KEY)//清除缓存
    @Override
    public void add(Res_Attr res_attr) {
        this.iResAttr.addForObject(res_attr);
    }

    @Override
    public String queryforobject1(Res_Attr res_attr) {
        return this.iResAttr.queryForObject1(res_attr);
    }

    @Override
    public Integer queryforResId(Res_Attr res_attr) {
        return this.iResAttr.queryForResId(res_attr);
    }
    @Override
    public String queryForMaxDeviceId(){
        return this.iResAttr.queryForMaxDeviceId();
    }


    @Override
    public Integer queryforResIdtoChannel(Integer ss) {
        return this.iResAttr.queryforResIdtoChannel(ss);
    }





    @Override
    public Res_Attr DeviceIDforRes(String s) {
        return this.iResAttr.DeviceIDforRes(s);
    }



    @Override
    public Res_Attr getResAttrOne(int id) {
        DynamicDataSourceContextHolder.setSlave();
        return this.iResAttr.getResAttrOne(id);
    }


    @Override
    public int searchForResAttrByIP(String ip) {
        return this.iResAttr.searchForResAttrByIP(ip);
    }

    @Override
    public List<Res_Attr> getVideoCameraTblMa(int page) {
        DynamicDataSourceContextHolder.setSlave();
        return this.iResAttr.getVideoCameraTblMa(page);
    }


//从联网平台获取设备数据

    @Override
    //从人脸布控系统获取设备数据
    public List<Res_Attr> getVideoCameraTbl2() {
        return this.iResAttr.getVideoCameraTbl();
    }
    @Override
    public String queryFortblServiceByIPAddress() {
        return this.iResAttr.queryFortblServiceByIPAddress();
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = CACHE_KEY)//清除缓存
    @Override
    public void update(Res_Attr attr) {
        this.iResAttr.updateForObject(attr);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = CACHE_KEY)//清除缓存
    @Override
    public void delete(Res_Attr res) {
        this.iResAttr.deleteForObject(res);
    }
}
