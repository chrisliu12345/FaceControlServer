package com.gd.dao.query;

import com.gd.domain.config.Camera;
import com.gd.domain.query.DetectImages;
import com.gd.domain.query.DetectImagesTemp;
import com.gd.domain.query.PeopleCollect;
import com.gd.domain.query.Record;
import com.gd.domain.userinfo.UserInfo;
import com.gd.domain.video.SimplePicture;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 郄梦岩 on 2017/12/23.
 */
@Repository("queryDao")
public interface IQueryDao {
    @Select("<script>SELECT B.*,A.REALNAME,A.ORG,A.PARENTORG,A.POLICENUM FROM  fr_original_record as B inner join sys_userinfo as A on A.CollectId = B.CollectId and A.BeIsDeleted=0 ORDER BY B.Date DESC limit 5</script>")
    List<DetectImagesTemp> queryForObject();

    @Select("<script>SELECT B.*,A.REALNAME,A.ORG,A.PARENTORG,A.POLICENUM FROM  fr_original_record as B inner join sys_userinfo as A on A.CollectId = B.CollectId and A.BeIsDeleted=0  and  B.Date Like CONCAT('%',#{nowdate},'%') ORDER BY B.Date DESC</script>")
  /*  @Select("<script>SELECT B.*,A.REALNAME,A.ORG,A.PARENTORG,A.POLICENUM FROM  fr_original_record as B inner join sys_userinfo as A on A.CollectId = B.CollectId and A.BeIsDeleted=0 ORDER BY B.Date DESC limit 100</script>")*/
    List<DetectImagesTemp> queryForObjectDistory(String nowdate);
    @Select("<script>SELECT B.*,A.REALNAME,A.ORG,A.PARENTORG,A.POLICENUM FROM  fr_original_record as B inner join sys_userinfo as A on A.CollectId = B.CollectId and A.BeIsDeleted=0 where B.EmployeeId=#{id} ORDER BY B.Date DESC limit 5</script>")
    List<DetectImagesTemp> queryForObject1(String id);
    @Select("<script>SELECT B.*,A.REALNAME,A.ORG,A.PARENTORG,A.POLICENUM FROM  fr_original_record as B inner join sys_userinfo as A on A.CollectId = B.CollectId and A.BeIsDeleted=0 where B.CamerId=#{Camid} ORDER BY B.Date DESC limit 5</script>")
    List<DetectImagesTemp> queryForObject2(String Camid);
    @Update("<script>UPDATE sys_userinfo set realName=#{realName},org=#{org},parentorg=#{parentorg},policeNum=#{policeNum} where ID=#{id}</script>")
    void updateForDectImg(UserInfo userInfo);

   /* @Select("<script>SELECT distinct CamerId,TaskType FROM fr_original_record </script>")*/
    @Select("<script>SELECT * FROM fr_camera </script>")
    List<Camera> getCameraName();

    @Select("<script>SELECT B.*,A.REALNAME,A.ORG,A.PARENTORG,A.POLICENUM,A.ID FROM  fr_original_record as B inner join sys_userinfo as A on A.CollectId = B.CollectId and  A.BeIsDeleted=0 and B.CamerId=#{CamerId} and B.TaskType=#{TaskType} and B.Date BETWEEN #{StartTime} and #{EndTime}" +
            "<if test=\"policeNum!=null\">\n" +
            "AND A.POLICENUM=#{policeNum}\n" +
            "</if>\n" +
            " ORDER BY B.id DESC</script>")

    List<DetectImagesTemp> queryForResultCamera(DetectImagesTemp detectImagesTemp);

    @Select("<script>SELECT B.*,A.REALNAME,A.ORG,A.PARENTORG,A.POLICENUM FROM  fr_attendance_record as B inner join sys_userinfo as A on A.CollectId = B.CollectId AND A.BeIsDeleted=0</script>")
    List<Record> getRecord();

    @Select("<script>SELECT B.*,A.REALNAME,A.ORG,A.PARENTORG,A.POLICENUM FROM  fr_attendance_record as B inner join sys_userinfo as A on A.CollectId = B.CollectId AND A.BeIsDeleted=0"+
            "<if test=\"CreateTime1!=null\">\n" +
            "AND B.CreateTime BETWEEN #{CreateTime1} and #{CreateTime2}\n" +
            "</if>\n" +
            "<if test=\"CollectId!=null\">\n" +
            "AND A.CollectId = #{CollectId}\n" +
            "</if>\n" +
            "<if test=\"realName!=null\">\n" +
            "AND A.REALNAME = #{realName}\n" +
            "</if>\n" +
            "<if test=\"org!=null\">\n" +
            "AND A.ORGID = #{org}\n" +
            "</if>\n" +
            "</script>")
    List<Record> queryForNameAndTime(Record record);
    @Select("<script>SELECT B.*,A.REALNAME,A.ORG,A.PARENTORG,A.POLICENUM FROM  fr_attendance_record as B inner join sys_userinfo as A on A.CollectId = B.CollectId and A.BeIsDeleted=0"+
            "<if test=\"CreateTime1!=null\">\n" +
            "AND B.CreateTime BETWEEN #{CreateTime1} and #{CreateTime2}\n" +
            "</if>\n" +
            "</script>")
    List<Record> queryForOrgs(Record record);
    @Select("<script>SELECT  Site FROM fr_camera WHERE CamerId=#{camera}</script>")
    String searchForSite(Integer camera);
    @Insert("<script>INSERT INTO fr_sample_photo (CollectId,RelativePath,UsedToUpdate,SelectedFlag) VALUES" +
            "(#{CollectId},#{RelativePath},#{UsedToUpdate},#{SelectedFlag})</script>")
    void updateForSimplePicture(SimplePicture simplePicture);
    @Update("<script>UPDATE fr_original_record SET EmployeeId=#{EmployeeId},CollectId=#{CollectId},BeforeNum=#{BeforeNum} WHERE CamerId=#{CamerId} and id=#{id}</script>")
    void updateForThisData(DetectImagesTemp detectImages);
    @Update("<script>UPDATE fr_person_collection SET UpdatedOrNot=#{UpdatedOrNot} WHERE EmployeeId=#{EmployeeId}</script>")
    void updateForPeopleCollect(PeopleCollect peopleCollect);
    @Select("<script>SELECT CollectId FROM fr_person_collection WHERE EmployeeId=#{exployed}</script>")
    Integer searchForcollectid(String exployed);
    @Select("<script>SELECT A.*,B.realName FROM fr_attendance_record as A inner join sys_userinfo as B on A.CollectId = B.CollectId AND A.id=#{id} AND A.BeIsDeleted=0</script>")
    Record getRecordResultOne(Integer id);
    @Select("<script>SELECT B.*,A.REALNAME,A.ORG,A.PARENTORG,A.POLICENUM FROM  fr_attendance_record as B inner join sys_userinfo as A on A.CollectId = B.CollectId AND A.BeIsDeleted=0"+
            "<if test=\"CreateTime1!=null\">\n" +
            "AND B.CreateTime BETWEEN #{CreateTime1} and #{CreateTime2}\n" +
            "</if>\n" +
            "<if test=\"EmployeeId!=null\">\n" +
            "AND A.POLICENUM = #{EmployeeId}\n" +
            "</if>\n" +
            "<if test=\"realName!=null\">\n" +
            "AND A.REALNAME = #{realName}\n" +
            "</if>\n" +
            "<if test=\"org!=null\">\n" +
            "AND A.ORGID = #{org}\n" +
            "</if>\n" +
            "</script>")
    List<Record> getRecordsToMap(Record record);
    @Select("<script>SELECT value FROM fr_config WHERE name='PictSavedHttpURL'</script>")
    String getPictSaveRootDirectory();
    @Select("<script>SELECT value FROM fr_config WHERE name='PictSaveRootDirectory'</script>")
    String getPictSaveRootDirectoryNew();

}

