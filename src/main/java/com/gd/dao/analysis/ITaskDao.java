package com.gd.dao.analysis;

import com.gd.domain.analysis.AnalysisRule;
import com.gd.domain.analysis.Task;
import com.gd.domain.analysis.TblAlarmLinkage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("taskDao")
public interface ITaskDao {

    /**
     * 查询任务常规编码
     *
     * @param table
     * @return
     */
    @Select("select gatcode,assistantdesc from ${table}")
    public List<Map<String, Object>> queryTaskGatCode(@Param("table") String table);


    /**
     * 查询任务
     *
     * @param paramMap
     * @return
     */
    @Select("<script> " +
            "SELECT taskid,tasktype,taskname,camid,sipid,videotype,camurl,videostarttime,videoendtime,serviceid,priorty,taskstatus,committime,begintime,expectendtime,progress FROM tbl_task where 1=1 " +
            "<if test=\"serviceid!=null\" >\n" +
            "            and serviceid in\n" +
            "            <foreach collection=\"serviceid\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\"camid!=null\" >\n" +
            "            and camid in\n" +
            "            <foreach collection=\"camid\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\"sipid!=null\" >\n" +
            "            and sipid in\n" +
            "            <foreach collection=\"sipid\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\"taskid!=null\" >\n" +
            "            and taskid in\n" +
            "            <foreach collection=\"taskid\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "</script>")
    @Results({
            @Result(property = "taskID", column = "taskid"),
            @Result(property = "analysisRules", column = "taskid", many = @Many(select = "queryAnalysisRulrByTaskId"))
    })
    public List<Task> queryTaskList(Map<String, Object> paramMap);

    /**
     * 查询任务的 AnalysisRule
     *
     * @param taskid
     * @return
     */
    @Select("select ruleid,taskid,analysisruleid,rulename,ruleCode,sensitiveness,hmin,vertex,interParams,camid,controlstatus from tbl_analysisrule  where taskid=#{taskid}")
    public List<AnalysisRule> queryAnalysisRulrByTaskId(int taskid);

    @Select("select ruleid,taskid,analysisruleid,rulename,ruleCode,sensitiveness,hmin,vertex,interParams,camid,controlstatus from tbl_analysisrule  ")
    public List<AnalysisRule> queryAnalysisRulrAll();


    /**
     * 插入新任务
     *
     * @param task
     * @return
     */
    @Insert("INSERT INTO tbl_task(taskid,tasktype,taskname,camid,sipid,videotype,camurl,videostarttime,videoendtime,serviceid,priorty,taskstatus,committime,begintime,expectendtime,progress) \n" +
            "VALUES (#{taskID},#{taskType},#{taskName},#{camID},#{sipID},#{videoType},#{camUrl},#{videoStartTime},#{videoEndTime},#{serviceID},#{priorty},#{taskStatus},NOW(),#{beginTime},#{expectEndTime},#{progress})")
    public int insertTask(Task task);

    @Insert("INSERT INTO tbl_analysisrule(ruleid,taskid,rulename,ruleCode,sensitiveness,hmin,vertex,interParams,camid,controlstatus)\n" +
            "VALUES (#{ruleID},#{taskID},#{ruleName},#{ruleCode},#{sensitiveness},#{hmin},#{vertex},#{interParams},#{camID},#{controlStatus})")
    public int insertTaskAnalysiRule(AnalysisRule analysisRule);


    @Select("SELECT  deviceid FROM tbl_res_attr WHERE resid=#{camid}")
    public String querySipIDbyCamid(int camid);

    @Select("SELECT  resid FROM tbl_res_attr WHERE deviceid=#{sipid}")
    public Integer queryCamIDbySipID(String sipid);

    @Update("UPDATE tbl_task SET taskstatus=#{status} WHERE taskid=#{taskid}")
    public int UpdateTaskStatus(@Param("taskid") int taskid, @Param("status") int status);

    @Update("UPDATE tbl_analysisrule SET controlstatus=#{status} WHERE ruleid=#{ruleid}")
    public int UpdateAnalysisRuleStatus(@Param("ruleid") int ruleid, @Param("status") int status);

    @Update("UPDATE tbl_task SET taskname=#{taskname} WHERE taskid=#{taskid}")
    public int UpdateTaskName(@Param("taskid") int taskid, @Param("taskname") String taskname);

    /**
     * @param paramMap
     * @return
     */
    @Select("<script> " +
            " select * from (select a.*,b.name as camname from  (select  abnormalid,ruletype, e.DetailName as ruletypename, s.ruleid,r.rulename, behaviorname,s.camid, behaviorvalue,occurtime,objecttype,objectid,serviceid,resultpath from tbl_analysisresult s,tbl_analysisrule r,tbl_event e where s.ruleid=r.ruleid and e.ClassCode=100 and e.TypeCode= s.ruletype  and occurtime  &gt;= str_to_date(#{starttime},&apos;%Y-%m-%d %H:%i:%s&apos;) and occurtime  &lt;  str_to_date(#{endtime},&apos;%Y-%m-%d %H:%i:%s&apos;) " +
            "<if test=\" camid!=null\" >\n" +
            "            and s.camid in\n" +
            "            <foreach collection=\"camid\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\"ruletype!=null\" >\n" +
            "            and s.ruletype in\n" +
            "            <foreach collection=\"ruletype\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            ") a left join tbl_res_attr b on a.camid=b.resid) c " +
            "</script>")
    public List<Map<String, Object>> queryAnalysisResult(Map<String, Object> paramMap);

    /**
     * 查询人的结构化数据
     *
     * @param paramMap
     * @return
     */
    @Select("<script> " +
            "select personid,camid, ra.name camename,personappeartime,bd.AssistantDesc bodytype,hi.assistantdesc hairstyletype,hc.assistantdesc haircolor,sex.assistantdesc sex,age.assistantdesc age,co.assistantdesc coatstyle,co1.assistantdesc coatcolor,cl.assistantdesc coatlenth,pa.assistantdesc  pantsstyle,co2.assistantdesc pantscolor,pl.assistantdesc pantslenth,ss.assistantdesc shoesstyle,co3.assistantdesc shoescolor,co4.assistantdesc breathmaskcolor,hs.assistantdesc hatstyle,gs.assistantdesc glassstyle,co5.assistantdesc scarfcolor,co6.assistantdesc umbrellacolor,bg.assistantdesc bagstyle,co7.assistantdesc bagcolor,appendage\n" +
            "from (select personid,camid,personappeartime,bodytype,hairstyletype,haircolor,sex,age,coatstyle,coatcolor,coatlenth,pantsstyle,pantscolor,pantslenth,shoesstyle,shoescolor,breathmaskcolor,hatstyle,glassstyle,scarfcolor,umbrellacolor,bagstyle,bagcolor,appendage,framenum,internum from tbl_personfeature where personappeartime  &gt;= str_to_date(#{starttime},&apos;%Y-%m-%d %H:%i:%s&apos;) and personappeartime  &lt;  str_to_date(#{endtime},&apos;%Y-%m-%d %H:%i:%s&apos;)\n" +
            "<if test=\" camid!=null\" >\n" +
            "            and camid in\n" +
            "            <foreach collection=\"camid\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" bodytype!=null\" >\n" +
            "            and bodytype in\n" +
            "            <foreach collection=\"bodytype\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" hairstyletype!=null\" >\n" +
            "            and hairstyletype in\n" +
            "            <foreach collection=\"hairstyletype\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" haircolor!=null\" >\n" +
            "            and haircolor in\n" +
            "            <foreach collection=\"haircolor\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" sex!=null\" >\n" +
            "            and sex in\n" +
            "            <foreach collection=\"sex\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" age!=null\" >\n" +
            "            and age in\n" +
            "            <foreach collection=\"age\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" coatstyle!=null\" >\n" +
            "            and coatstyle in\n" +
            "            <foreach collection=\"coatstyle\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" coatcolor!=null\" >\n" +
            "            and coatstyle in\n" +
            "            <foreach collection=\"coatstyle\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" coatlenth!=null\" >\n" +
            "            and coatlenth in\n" +
            "            <foreach collection=\"coatlenth\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" pantsstyle !=null\" >\n" +
            "            and pantsstyle in\n" +
            "            <foreach collection=\"pantsstyle\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" pantscolor !=null\" >\n" +
            "            and pantscolor in\n" +
            "            <foreach collection=\"pantscolor\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" pantslenth !=null\" >\n" +
            "            and pantslenth in\n" +
            "            <foreach collection=\"pantslenth\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" shoesstyle !=null\" >\n" +
            "            and shoesstyle in\n" +
            "            <foreach collection=\"shoesstyle\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" shoescolor !=null\" >\n" +
            "            and shoescolor in\n" +
            "            <foreach collection=\"shoescolor\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" breathmaskcolor !=null\" >\n" +
            "            and breathmaskcolor in\n" +
            "            <foreach collection=\"breathmaskcolor\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" hatstyle !=null\" >\n" +
            "            and hatstyle in\n" +
            "            <foreach collection=\"hatstyle\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" glassstyle !=null\" >\n" +
            "            and glassstyle in\n" +
            "            <foreach collection=\"glassstyle\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" scarfcolor !=null\" >\n" +
            "            and scarfcolor in\n" +
            "            <foreach collection=\"scarfcolor\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" umbrellacolor !=null\" >\n" +
            "            and umbrellacolor in\n" +
            "            <foreach collection=\"umbrellacolor\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" bagstyle !=null\" >\n" +
            "            and bagstyle in\n" +
            "            <foreach collection=\"bagstyle\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" bagcolor !=null\" >\n" +
            "            and bagcolor in\n" +
            "            <foreach collection=\"bagcolor\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            ")  d " +
            "left join tbl_body bd on d.bodyType=bd.GATcode\n" +
            "left join tbl_hair hi on d.HairStyleType= bd.GATcode\n" +
            "left join tbl_color hc on d.HairColor = hc.GATCode\n" +
            "left join tbl_sex sex on d.sex=sex.gatcode\n" +
            "left join tbl_age age on d.age= age.gatcode\n" +
            "left join tbl_coat co on d.coatstyle= co.gatcode\n" +
            "left join tbl_color co1 on d.coatcolor=co1.gatcode\n" +
            "left join tbl_coatlength cl on d.coatlenth= cl.gatcode\n" +
            "left join tbl_pants pa on d.pantsstyle= pa.gatcode\n" +
            "left join tbl_color co2 on d.pantscolor=co2.gatcode\n" +
            "left join tbl_pantslen pl  on d.pantslenth=pl.gatcode\n" +
            "left join tbl_shoesstyle ss on d.shoesstyle=ss.gatcode\n" +
            "left join tbl_color co3 on d.shoescolor=co3.gatcode\n" +
            "left join tbl_color co4 on d.breathmaskcolor=co4.gatcode\n" +
            "left join tbl_hatstyle hs on d.hatstyle=hs.gatcode\n" +
            "left join tbl_glassstyle gs on d.glassstyle=gs.gatcode\n" +
            "left join tbl_color co5 on d.scarfcolor=co5.gatcode\n" +
            "left join tbl_color co6 on d.umbrellacolor=co6.gatcode\n" +
            "left join tbl_bagstyle bg on d.bagstyle=bg.gatcode\n" +
            "left join tbl_color co7 on d.bagcolor=co7.gatcode\n" +
            "left join tbl_res_attr ra on d.camid=ra.ResID\n" +
            "</script>")
    public List<Map<String, Object>> queryPersonFeature(Map<String, Object> paramMap);

    @Select("<script>" +
            "select vehicleid,(case infotype when 0 then '人工采集' when 1 then '自动采集' end) infotype,camid,re.name camname,appeartime,(case hasplate when 1 then '有' when 0 then'无' end) hasplate,pc.AssistantDesc plateclass,plateno,platenoattach,co1.AssistantDesc platecolor,platereliability,platecharreliability,(case isdecked when 0 then '无' when 1 then '是' end) isdecked,(case isaltered when 0 then '无' when 1 then '是' end) isaltered,(case iscovered when 0 then '无' when 1 then '是' end)  iscovered,vco.AssistantDesc vehiclecolor,(case vehiclecolordept when 0 then '浅' when 1 then '深' end) vehiclecolordept,vc.AssistantDesc vehicleclass,vehiclemodel,vcb.assistantdesc vehiclebrand,brandrealiability,vehiclefrontitem,vehiclerearitem,(case sunvisor when 0 then '收起' when 1 then '放下' end) sunvisor,(case safetybelt when 0 then '未系' when 1 then '有系' end) safetybelt,(case calling when 0 then '未打电话' when 1 then '电话中' end) calling,(case isfront when 0 then '车尾' when 1 then '车头' end) isfront \n" +
            "from\n" +
            "(select vehicleid,infotype,camid,appeartime,hasplate,plateclass,plateno,platenoattach,platecolor,platereliability,platecharreliability,isdecked,isaltered,iscovered,vehiclecolor,vehiclecolordept,vehicleclass,vehiclemodel,vehiclebrand,brandrealiability,vehiclefrontitem,vehiclerearitem,sunvisor,safetybelt,calling,isfront from tbl_vehicle where appeartime  &gt;= str_to_date(#{starttime},&apos;%Y-%m-%d %H:%i:%s&apos;) and appeartime  &lt;  str_to_date(#{endtime},&apos;%Y-%m-%d %H:%i:%s&apos;)" +
            "<if test=\" camid!=null\" >\n" +
            "            and camid in\n" +
            "            <foreach collection=\"camid\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" hasplate !=null\" >\n" +
            "            and hasplate in\n" +
            "            <foreach collection=\"hasplate\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" plateclass !=null\" >\n" +
            "            and plateclass in\n" +
            "            <foreach collection=\"plateclass\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" plateno !=null\" >\n" +
            "            and plateno in\n" +
            "            <foreach collection=\"plateno\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" platenoattach !=null\" >\n" +
            "            and platenoattach in\n" +
            "            <foreach collection=\"platenoattach\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" platecolor !=null\" >\n" +
            "            and platecolor in\n" +
            "            <foreach collection=\"platecolor\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" isdecked !=null\" >\n" +
            "            and isdecked in\n" +
            "            <foreach collection=\"isdecked\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" isaltered !=null\" >\n" +
            "            and isaltered in\n" +
            "            <foreach collection=\"isaltered\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" iscovered !=null\" >\n" +
            "            and iscovered in\n" +
            "            <foreach collection=\"iscovered\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" vehiclecolor !=null\" >\n" +
            "            and vehiclecolor in\n" +
            "            <foreach collection=\"vehiclecolor\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" vehiclecolordept !=null\" >\n" +
            "            and vehiclecolordept in\n" +
            "            <foreach collection=\"vehiclecolordept\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" vehicleclass !=null\" >\n" +
            "            and vehicleclass in\n" +
            "            <foreach collection=\"vehicleclass\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" vehiclemodel !=null\" >\n" +
            "            and vehiclemodel in\n" +
            "            <foreach collection=\"vehiclemodel\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" vehiclebrand !=null\" >\n" +
            "            and vehiclebrand in\n" +
            "            <foreach collection=\"vehiclebrand\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" sunvisor !=null\" >\n" +
            "            and sunvisor in\n" +
            "            <foreach collection=\"sunvisor\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" safetybelt !=null\" >\n" +
            "            and safetybelt in\n" +
            "            <foreach collection=\"safetybelt\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" calling !=null\" >\n" +
            "            and calling in\n" +
            "            <foreach collection=\"calling\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            "<if test=\" isfront !=null\" >\n" +
            "            and isfront in\n" +
            "            <foreach collection=\"isfront\" open=\"(\" separator=\",\" close=\")\" item=\"item\" index=\"index\">\n" +
            "                #{item}\n" +
            "            </foreach>\n" +
            " </if>" +
            ")d\n" +
            "left join tbl_plateclass pc on d.plateclass = pc.gatcode\n" +
            "left join tbl_color co1 on d.platecolor=co1.gatcode\n" +
            "left join tbl_vehiclecolor vco on d.platecolor=vco.gatcode\n" +
            "left join tbl_vehicleclass vc on d.vehicleclass=vc.gatcode \n" +
            "left join tbl_vehiclebrand vcb on d.vehicleclass=vcb.gatcode\n" +
            "left join tbl_res_attr re  on d.camid= re.resid " +
            "</script>")
    public List<Map<String, Object>> queryVehicle(Map<String, Object> paramMap);

    @Select("<script>SELECT  id,Alarm_event_name as alarmEventName,Deviceid,Input_channel as inputChannel," +
            " AlarmMethod,AlarmType,Notified_person as notifiedPerson,linkage_Method as linkageMethod,linkage_Camera as linkageCamera,linkage_Info as linkageInfo FROM tbl_alarm_linkage WHERE 1=1"
            + "<if test=\"id!=null and id!=''\">" +
            "AND id=#{id}" +
            "</if>"
            + "<if test=\"alarm_event_name!=null and alarm_event_name!=''\">" +
            "AND alarm_event_name=#{alarm_event_name}" +
            "</if>"
            + "<if test=\"deviceId!=null and deviceId!=''\">" +
            "AND Deviceid=#{deviceId}" +
            "</if>"
            + "<if test=\"input_channel!=null and input_channel!=''\">" +
            "AND Input_channel=#{input_channel}" +
            "</if>"
            + "<if test=\"alarmMethod!=null and alarmMethod!=''\">" +
            "AND AlarmMethod=#{alarmMethod}" +
            "</if>"
            + "<if test=\"alarmType!=null and alarmType!=''\">" +
            "AND AlarmType=#{alarmType}" +
            "</if>"
            + "<if test=\"notified_person!=null and notified_person!=''\">" +
            "AND Notified_person=#{notified_person}" +
            "</if>"
            + "<if test=\"linkage_method!=null and linkage_method!=''\">" +
            "AND Linkage_Method=#{linkage_method}" +
            "</if>"
            + "<if test=\"linkage_camera!=null and linkage_camera!=''\">" +
            "AND Linkage_Camera=#{linkage_camera}" +
            "</if>"
            + "<if test=\"linkage_info!=null and linkage_info!=''\">" +
            "AND Linkage_Info=#{linkage_info}" +
            "</if>"
            + "</script>")
    List<TblAlarmLinkage> queryAlarmLinkageList(Map<String, Object> map);

}
