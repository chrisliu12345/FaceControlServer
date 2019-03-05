package com.gd.service.query.impl;

import com.gd.dao.query.IQueryDao;
import com.gd.domain.config.Camera;
import com.gd.domain.query.DetectImages;
import com.gd.domain.query.DetectImagesTemp;
import com.gd.domain.query.PeopleCollect;
import com.gd.domain.query.Record;
import com.gd.domain.userinfo.UserInfo;
import com.gd.domain.video.SimplePicture;
import com.gd.service.query.IQueryService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 郄梦岩 on 2017/12/23.
 */
@Service("QueryService")
public class QueryServiceImpl implements IQueryService {
    @Autowired
    private IQueryDao queryDao;

    @Override
    public List<Camera> getCamera() {
        return this.queryDao.getCameraName();
    }

    @Override
    public void updateForPeopleCollect(PeopleCollect peopleCollect) {
        this.queryDao.updateForPeopleCollect(peopleCollect);
    }


    @Override
    public void updateForThisData(DetectImagesTemp detectImages) {
        this.queryDao.updateForThisData(detectImages);
    }

    @Override
    public Integer searchForcollectid(String exployed) {
        return this.queryDao.searchForcollectid(exployed);
    }

    @Override
    public String getPictSaveRootDirectory() {
        return this.queryDao.getPictSaveRootDirectory();
    }

    @Override
    public String getPictSaveRootDirectoryNew() {
        return this.queryDao.getPictSaveRootDirectoryNew();
    }

    @Override
    public List<Record> getRecordsToMap(Record record) {
        return this.queryDao.getRecordsToMap(record);
    }

    @Override
    public Record getRecordResultOne(Integer id) {
        return this.queryDao.getRecordResultOne(id);
    }

    @Override
    public List<DetectImagesTemp> queryForObject2(String Camid) {
        return this.queryDao.queryForObject2(Camid);
    }

    @Override
    public List<DetectImagesTemp> queryForObjectDistory() {
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String nowDate=sdf.format(d);
      /*  String nowDate="2018-06-05";*/
        System.out.println("我是当前日期:"+sdf.format(d));
        return this.queryDao.queryForObjectDistory(nowDate);
    }

    @Override
    public void updateForSimplePicture(SimplePicture simplePicture) {
        this.queryDao.updateForSimplePicture(simplePicture);
    }

    @Override
    public String searchForSite(Integer camera) {
        return this.queryDao.searchForSite(camera);
    }

    @Override
    public List<Record> queryForOrgs(Record record) {
        return this.queryDao.queryForOrgs(record);
    }



    @Override
    public List<Record> queryForNameAndTime(Record record) {
        return this.queryDao.queryForNameAndTime(record);
    }

    @Override
    public List<Record> getAllRecord() {
        return this.queryDao.getRecord();
    }

    @Override
    public List<DetectImagesTemp> queryForResultCamera(DetectImagesTemp detectImagesTemp) {
        return this.queryDao.queryForResultCamera(detectImagesTemp);

    }

    @Override
    public void updateForDectImg(UserInfo userInfo) {
        this.queryDao.updateForDectImg(userInfo);
    }

    @Override
    public List<DetectImagesTemp> queryForObject() {
        return this.queryDao.queryForObject();
    }
    @Override
    public List<DetectImagesTemp> queryForObject1(String id) {
        return this.queryDao.queryForObject1(id);
    }

}
