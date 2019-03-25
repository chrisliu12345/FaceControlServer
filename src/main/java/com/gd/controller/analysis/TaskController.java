package com.gd.controller.analysis;

import com.gd.controller.common.SSEControler;
import com.gd.domain.HandResult;
import com.gd.domain.SseData;
import com.gd.domain.analysis.*;
import com.gd.service.analysis.ITaskService;
import com.gd.util.HttpUtil;
import com.gd.util.StringUtils;
import com.gd.util.TimeUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@RestController
@RequestMapping("/task")
public class TaskController {
    private static Logger log = LoggerFactory.getLogger(TaskController.class);
    public  static  int AnalysisStateStarting=1;
    public static int TaskTypeBehavir=100;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private SSEControler sseControler;

    @Autowired
    @Qualifier("sseBlockingQueue")
    private BlockingQueue<SseData> sseBlockingQueue;

    @Value("${c.task.structing.start.url}")
    private String structingstarturl;

    @Value("${c.task.structing.stop.url}")
    private String structingstopurl;

    @Value("${c.task.behavior.start.url}")
    private String behaviorstarturl;

    @Value("${c.task.behavior.stop.url}")
    private String behaviorstopurl;

    @RequestMapping("/gatcode/{gattype}")
    public String queryTaskGatCode(@PathVariable("gattype") int gattype){
        List<Map<String, Object>> maps = taskService.queryTaskGatCode(gattype);
        Gson gson = new GsonBuilder().create();
        HandResult<List<Map<String, Object>>> result = new HandResult<>();
        result.setCode(HandResult.Success);
        result.setData(maps);
        return  gson.toJson(maps);
    }

    /**
     * 查询任务列表
     * @param paramMap 条件列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public String  queryTaskList(@RequestBody Map<String,Object> paramMap) {
        List<Task> taskList = taskService.queryTaskList(paramMap);
        Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();
        HandResult<List<Task>> result = new HandResult<>();
        result.setCode(HandResult.Success);
        result.setData(taskList);
        return gson.toJson(result);
    }

   /* *//**
     *
     * @param taskstr
     * @return
     *//*
    @RequestMapping("/create")
    public String creatTask(@RequestBody Task task){
        taskService.createTask(task);
        Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();

        return "";
    }*/

    /**
     * 创建新的视频分析任务
     * @param taskstr 接收字符串（因时间字符串不能直接转化为timestame 的问题，不能直接使用类）
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String creatTask(@RequestBody String taskstr) {
        String responsestr;
        Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();
        Task task = gson.fromJson(taskstr, Task.class);
        int n = taskService.createTask(task);
        if (n > 0) {
            Map<String, Object> querynewtask = new HashMap<>();
            querynewtask.put("taskid", task.getTaskID().toString());
            List<Task> tasks = taskService.queryTaskList(querynewtask);
            HandResult<Task> result = new HandResult<>();
            result.setData(task);
            result.setCode(HandResult.Success);
            responsestr = gson.toJson(result);
        } else {
            HandResult<String> result = new HandResult<>();
            result.setData("数据未成功入库，请查看日志");
            result.setCode(HandResult.APPError);
            responsestr = gson.toJson(result);
        }
        return responsestr;
    }

    @RequestMapping(value = "/ar/create",method = RequestMethod.POST)
    public String creatAnalysisRule(@RequestBody String taskstr) {
        String responsestr;
        Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();
        AnalysisRule analysisRule = gson.fromJson(taskstr, AnalysisRule.class);
        int n = taskService.createTaskAnalysisRule(analysisRule);
        HandResult<AnalysisRule> result = new HandResult<>();
        result.setData(analysisRule);
        result.setCode(HandResult.Success);
        responsestr = gson.toJson(result);
        return responsestr;
    }

    @RequestMapping("/rulestatus/{taskid}/{ruleid}/{status}")
    public String changeRuleStatus(@PathVariable("taskid") int taskid,@PathVariable("ruleid") int ruleid, @PathVariable("status")int status) {
        int r = taskService.changeAnalysisRuleStatus(ruleid, status);

        HandResult<String> handResult = new HandResult<>();
        handResult.setCode(HandResult.Success);
        Gson gson = new Gson();
        String re = gson.toJson(handResult);
        return re;
    }

    @RequestMapping("/taskname/{taskid}/{taskname}")
    public String modifyTaskName(@PathVariable("taskid") int taskid,@PathVariable("taskname") String taskname) {
        int r = taskService.modifyTaskName(taskid, taskname);
        HandResult<String> handResult = new HandResult<>();
        handResult.setCode(HandResult.Success);
        Gson gson = new Gson();
        String re = gson.toJson(handResult);
        return re;
    }

    @RequestMapping("/status/{taskid}/{tasktype}/{status}")
    public String changeTaskStatus(@PathVariable("taskid") int taskid,@PathVariable("tasktype") int tasktype, @PathVariable("status")int status) {
        int r = taskService.changeTaskStatus(taskid, status);

        HttpUtil.ResposeData resposeData = changeAnalysisRuleState(taskid, status, tasktype);

        HandResult<String> handResult = new HandResult<>();
        handResult.setCode(HandResult.Success);
        Gson gson = new Gson();
        String re = gson.toJson(handResult);
        return re;
    }

    @RequestMapping("/page/{pageindex}")
    public String  pagefilter(@PathVariable("pageindex") int pageindex){
         PageHelper.startPage(pageindex, 2);
        List<AnalysisRule> analysisRules = taskService.queryroolList();
        PageInfo<AnalysisRule> pageInfo = new PageInfo<AnalysisRule>(analysisRules);
        Gson gson =new GsonBuilder().create();
        return gson.toJson(pageInfo);
    }

    /**
     * 查询任务列表
     * @param paramMap 条件列表
     * @return
     */
    @RequestMapping(value = "/analysisresult/{pageindex}/{pageSize}",method = RequestMethod.POST)
    public String  queryTaskList(@RequestBody Map<String,Object> paramMap,@PathVariable int pageindex,@PathVariable int pageSize) {
        PageHelper.startPage(pageindex, pageSize);
        List<Map<String,Object>>  taskList = taskService.queryAnalysisResult(paramMap);
        Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();
        PageInfo<Map<String,Object>> pageInfo =new PageInfo<Map<String,Object>>(taskList);

        HandResult<PageInfo<Map<String,Object>>> result = new HandResult<>();
        result.setCode(HandResult.Success);
        result.setData(pageInfo);
        return gson.toJson(result);
    }

    /**
     * 查询人员结构化数据
     * @param paramMap 查询条件
     * @param pageindex 页号（从1开始）
     * @param pageSize 每页数据条数
     * @return
     */
    @RequestMapping(value = "/personfeatureresult/{pageindex}/{pageSize}",method = RequestMethod.POST)
    public String  personFeatuReresult(@RequestBody Map<String,Object> paramMap,@PathVariable int pageindex,@PathVariable int pageSize) {
        PageHelper.startPage(pageindex, pageSize);
        List<Map<String,Object>>  taskList = taskService.queryPersonFeature(paramMap);
        Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();
        PageInfo<Map<String,Object>> pageInfo =new PageInfo<Map<String,Object>>(taskList);

        HandResult<PageInfo<Map<String,Object>>> result = new HandResult<>();
        result.setCode(HandResult.Success);
        result.setData(pageInfo);
        return gson.toJson(result);
    }

    /**
     * 查询人员结构化数据
     * @param paramMap 查询条件
     * @param pageindex 页号（从1开始）
     * @param pageSize 每页数据条数
     * @return
     */
    @RequestMapping(value = "/vehicleresult/{pageindex}/{pageSize}",method = RequestMethod.POST)
    public String  vehicleesultReresult(@RequestBody Map<String,Object> paramMap,@PathVariable int pageindex,@PathVariable int pageSize) {
        PageHelper.startPage(pageindex, pageSize);
        List<Map<String,Object>>  taskList = taskService.queryVehicle(paramMap);
        Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();
        PageInfo<Map<String,Object>> pageInfo =new PageInfo<Map<String,Object>>(taskList);

        HandResult<PageInfo<Map<String,Object>>> result = new HandResult<>();
        result.setCode(HandResult.Success);
        result.setData(pageInfo);
        return gson.toJson(result);
    }
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public String  test() {
       /* System.out.println(" test thread id" + Thread.currentThread().getId());
        sseBlockingQueue.offer("abc");
        sseControler.pushData();*/
       return "";
    }

    /**
     * 新增异常行为分析结果
     * @param analysisResult
     * @return
     */
    @RequestMapping(value = "/newAnalysisResult",method = RequestMethod.POST)
    public String newAnalysisResult(@RequestBody Map<String,Object> analysisResult) throws ParseException {
        AnalysisResult analysisResult1 = new AnalysisResult();
        analysisResult1.setRuleType(analysisResult.containsKey ("type")?(int)analysisResult.get("type"):null);
        analysisResult1.setBehaviorName(analysisResult.containsKey ("behaviorName")?analysisResult.get("behaviorName").toString():null);
        analysisResult1.setCamID(analysisResult.containsKey ("camID")?(int)analysisResult.get("camID"):null);
        analysisResult1.setOccurTime(analysisResult.containsKey ("occurTime")?new Timestamp(TimeUtils.strToDate( analysisResult.get("occurTime").toString()).getTime()):null);
        analysisResult1.setObjectType(analysisResult.containsKey ("objectType")?(int)analysisResult.get("objectType"):null);
        analysisResult1.setObjectID(analysisResult.containsKey ("objectID")?(int)analysisResult.get("objectID"):null);
        analysisResult1.setServiceID(analysisResult.containsKey ("serviceID")?(int)analysisResult.get("serviceID"):null);
        analysisResult1.setResultPath(analysisResult.containsKey ("resultPath")?(String)analysisResult.get("resultPath"):null);

        taskService.insertAnalysisResult(analysisResult1);

        Map<String,Object> params = new HashMap<>();
        params.put("deviceid",analysisResult.get("camid"));
        params.put("alarmtype",analysisResult.get("type"));
        List<TblAlarmLinkage> tblAlarmLinkages = taskService.queryAlarmLink(null);
        Map<String, HandResult<TblAlarmNotice>> handResultMap = new HashMap<>();
        for (TblAlarmLinkage link : tblAlarmLinkages) {
            analysisResult.put("alarmTypeName",link.getAlarmTypeName());
            analysisResult.put("camName",link.getDeviceName());

            String notifiedPerson = link.getNotifiedPerson();
            if (!StringUtils.isNullOrEmpty(notifiedPerson)) {
                String[] split = notifiedPerson.split(",");
                for (String s : split) {
                    SseData sseData = new SseData();
                    sseData.setClientflag(s);
                    sseData.setCode(100);
                    sseData.setData(analysisResult);
                    sseBlockingQueue.offer(sseData);
                    sseControler.pushData();
                }
            }
        }
        return "";
    }

    /**
     * 向C++发送服务请求
     * @param taskid
     * @param status
     * @param tasktype
     * @return
     */
    private HttpUtil.ResposeData changeAnalysisRuleState(int taskid,int status,int tasktype) {
        String url = null;
        String jsonstr = null;
        //结构化和人脸侦查
        if (tasktype != TaskTypeBehavir) {
            jsonstr = "[{\"taskID\":\"" + taskid + "\"}]";
            if (status == AnalysisStateStarting) {
                url = structingstarturl;
            } else {
                url = structingstopurl;
            }
        } else {
            if (status == AnalysisStateStarting) {
                url = behaviorstarturl + taskid;
            } else {
                url = behaviorstopurl + taskid;
            }
        }
        log.info("向：" + url + "-body:" + jsonstr + "发送请求");
        HttpUtil.ResposeData resposeData = HttpUtil.httpPost(url, "", null, jsonstr, HttpUtil.ContentJson, null);
        log.info(url + "应答" + resposeData.getCode() + ":" + resposeData.getData());
        return resposeData;
    }


    /**
     * 查询已配置异常行为分析的相机
     * @return
     */
    @RequestMapping(value = "/queryAnalysisCamera")
    public String queryAnalysisCamera(){
        List<Map<String, Object>> maps = taskService.queryAnalysisCamera();
        HandResult<List<Map<String, Object>>> result = new HandResult<>();
        Gson gson = new GsonBuilder().create();
        result.setCode(HandResult.Success);
        result.setData(maps);
        return gson.toJson(result);
    }


    @RequestMapping(value = "/queryalarmlink",method = RequestMethod.POST)
    public String queryAlarmLink(@RequestBody Map<String,Object> queryalarmlink){
        List<TblAlarmLinkage> tblAlarmLinkages = taskService.queryAlarmLink(queryalarmlink);
        Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();
        HandResult<List<TblAlarmLinkage>> result = new HandResult<>();
        result.setCode(HandResult.Success);
        result.setData(tblAlarmLinkages);
        return gson.toJson(result);
    }

    /**
     * 新建告警联动
     * @param tblAlarmLinkage 接收字符串（因时间字符串不能直接转化为timestame 的问题，不能直接使用类）
     * @return
     */
    @RequestMapping(value = "/newalarmlink",method = RequestMethod.POST)
    public String insertAlarmLink(@RequestBody String tblAlarmLinkage, Principal principal) {
        Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();
        TblAlarmLinkage tblAlarmLinkage1 = gson.fromJson(tblAlarmLinkage, TblAlarmLinkage.class);
        tblAlarmLinkage1.setCreateUser(principal.getName());
        int id = taskService.insertAlarmLink(tblAlarmLinkage1);

        HandResult<Integer> result = new HandResult<>();
        result.setCode(HandResult.Success);
        result.setData(id);
        return gson.toJson(result);
    }


    /**
     * 更新告警联动数据
     * @param tblAlarmLinkage
     * @param principal
     * @return
     */
    @RequestMapping(value = "/updatealarmlink",method = RequestMethod.POST)
    public String updateAlarmLink(@RequestBody String tblAlarmLinkage, Principal principal) {
        Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();
        TblAlarmLinkage tblAlarmLinkage1 = gson.fromJson(tblAlarmLinkage, TblAlarmLinkage.class);
        tblAlarmLinkage1.setCreateUser(principal.getName());
        int id = taskService.updateTblAlarmLinkage(tblAlarmLinkage1);
        HandResult<Integer> result = new HandResult<>();
        result.setCode(HandResult.Success);
        result.setData(1);
        return gson.toJson(result);
    }

    /**
     * 删除告警联动
     * @param linkid
     * @param principal
     * @return
     */
    @RequestMapping(value = "/deletealarmlink/{linkid}")
    public String updateAlarmLink(@PathVariable("linkid") int linkid, Principal principal) {
        int id = taskService.deleteTblAlarmLinkage(linkid);
        HandResult<Integer> result = new HandResult<>();
        result.setCode(HandResult.Success);
        result.setData(1);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(result);
    }
}
