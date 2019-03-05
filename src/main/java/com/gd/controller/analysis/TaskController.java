package com.gd.controller.analysis;

import com.gd.domain.HandResult;
import com.gd.domain.analysis.AnalysisResult;
import com.gd.domain.analysis.AnalysisRule;
import com.gd.domain.analysis.Task;
import com.gd.service.analysis.ITaskService;
import com.gd.util.TimeUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @RequestMapping("/gatcode/{gattype}")
    public String queryTaskGatCode(@PathVariable("gattype") int gattype){
        List<Map<String, Object>> maps = taskService.queryTaskGatCode(gattype);
        Gson gson = new GsonBuilder().create();
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

    @RequestMapping("/rulestatus/{ruleid}/{status}")
    public String changeRuleStatus(@PathVariable("ruleid") int ruleid, @PathVariable("status")int status) {
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

    @RequestMapping("/status/{taskid}/{status}")
    public String changeTaskStatus(@PathVariable("taskid") int taskid, @PathVariable("status")int status) {
        int r = taskService.changeTaskStatus(taskid, status);
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
        String[] abc={"abc","ddd"};
        int[] in ={123,234};
        Map<String,Object> map= new HashMap<>();
        map.put("a",abc);
        map.put("b",in);
        Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();
        return gson.toJson(map);
    }
}