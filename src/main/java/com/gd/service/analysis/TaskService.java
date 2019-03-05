package com.gd.service.analysis;

import com.gd.dao.analysis.ITaskDao;
import com.gd.domain.analysis.AnalysisRule;
import com.gd.domain.analysis.Task;
import com.gd.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("taskService")
public class TaskService implements ITaskService{

    @Autowired
    private ITaskDao taskDao;

    @Override
    public List<Map<String, Object>> queryTaskGatCode(int tabletype){
        String tablename=null;
        switch (tabletype) {
            case 1:
                tablename = "tbl_vevent";
                break;
            case 2:
                tablename = "tbl_age";
                break;
            case 3:
                tablename = "tbl_body";
                break;
            case 4:
                tablename = "tbl_hair";
                break;
            case 5:
                tablename = "tbl_color";
                break;
            case 6:
                tablename = "tbl_sex";
                break;
            case 7:
                tablename = "tbl_coat";
                break;
            case 8:
                tablename = "tbl_coatlength";
                break;
            case 9:
                tablename = "tbl_paints";
                break;
            case 10:
                tablename = "tbl_pantslen";
                break;
            case 11:
                tablename = "tbl_shoesstyle";
                break;
            case 12:
                tablename = "tbl_hatstyle";
                break;
            case 13:
                tablename = "tbl_glassstyle";
                break;
            case 14:
                tablename = "tbl_bagstyle";
                break;
            case 15:
                tablename = "tbl_vehiclecolor";
                break;
            case 16:
                tablename = "tbl_vehicleclass";
                break;
            case 17:
                tablename = "tbl_vehiclebrand";
                break;
            default:
                 ;
        }
        return taskDao.queryTaskGatCode(tablename);

    }

    /**
     * Query Task And AnalysisRule By Condition
     * @param paramMap
     * @return
     */
    @Override
    public List<Task> queryTaskList(Map<String, Object> paramMap) {
         /* for (String key : paramMap.keySet()) {
            String services = paramMap.get(key).toString();
            String[] servicearr = services.split((","));
            paramMap.put(key, servicearr);
          switch (key) {
                case "serviceid":
                    String services = paramMap.get(key).toString();
                    String[] servicearr = services.split((","));
                    paramMap.put(key, servicearr);
                    break;
                case "camid":
                    String camids = paramMap.get(key).toString();
                    String[] camidarr = camids.split((","));
                    paramMap.put(key, camidarr);
                    break;
                case "sipid":
                    String sipids = paramMap.get(key).toString();
                    String[] sipidsarr = sipids.split((","));
                    paramMap.put(key, sipidsarr);
                    break;
                default:
                    String vs = paramMap.get(key).toString();
                    String[] varr = vs.split((","));
                    if (varr.length > 0) {

                    }
                    break;
            }
        }*/
        return taskDao.queryTaskList(paramMap);
    }

    /**
     * Create Task And AnalysisRule
     * @param task
     * @return
     */
    @Override
    @Transactional
    public int createTask(Task task) {
        //任务ID设置为当前UTC
        int taskid = (int) (System.currentTimeMillis() / 1000);
        task.setTaskID(taskid);

        //以sipid 设置camid
        if (task.getCamID() == null && !StringUtils.isNullOrEmpty(task.getSipID())) {
            Integer camid = taskDao.queryCamIDbySipID(task.getSipID());
            task.setCamID(camid);
        }
        //以camid设置sipid
        if (task.getCamID() != null && StringUtils.isNullOrEmpty(task.getSipID())) {
            String sipid = taskDao.querySipIDbyCamid(task.getCamID());
            task.setSipID(sipid);
        }
        //如果前端未给值,则初始化为3 已创建
        if(task.getTaskStatus()==null){
            task.setTaskStatus(3);
        }
        //插入task 表
        int result = createMainTask(task);

        List<AnalysisRule> analysisRules = task.getAnalysisRules();
        if (analysisRules != null) {
            for (AnalysisRule analysisRule : analysisRules) {
                analysisRule.setCamID(task.getCamID());
                analysisRule.setTaskID(taskid);
                analysisRule.setRuleID(taskid++);
                //如果前端未给值,则初始化为3 已创建
                if(analysisRule.getControlStatus()==null){
                    analysisRule.setControlStatus(3);
                }

                //插analysisrule表
                int n = createTaskAnalysisRule(analysisRule);
            }
        }
        return result;
    }

    /**
     * Create Task Only
     * @param task
     * @return
     */
    @Override
    public int createMainTask(Task task){
       int n = taskDao.insertTask(task);
       return n;
    }

    /**
     * Create Task AnalysisRule
     * @param analysisRule
     * @return
     */
    @Override
    public int createTaskAnalysisRule(AnalysisRule analysisRule){
        int n = taskDao.insertTaskAnalysiRule(analysisRule);
        return n;
    }

    /**
     * 修改AnalysisRule状态
     * @param ruleid
     * @param status
     * @return
     */
    @Override
    public int changeAnalysisRuleStatus(int ruleid, int status) {
        return taskDao.UpdateAnalysisRuleStatus(ruleid,status);
    }

    /**
     * 修改任务名称
     * @param taskid
     * @param taskname
     * @return
     */
    public int modifyTaskName(int taskid, String taskname){
       return taskDao.UpdateTaskName(taskid,taskname);
    }

    @Override
    public int changeTaskStatus(int taskid, int status) {
        return taskDao.UpdateTaskStatus(taskid, status);
    }

    @Override
    public List<AnalysisRule> queryroolList(){
      return  taskDao.queryAnalysisRulrAll() ;
    }

    @Override
    public List<Map<String,Object>> queryAnalysisResult(Map<String, Object> paramMap) {
        /*
        for (String key : paramMap.keySet()) {
            switch (key) {
                case "ruletype":
                    String ruletypes = paramMap.get(key).toString();
                    String[] ruletypearr = ruletypes.split((","));
                    paramMap.put(key, ruletypearr);
                    break;
                case "camid":
                    String camids = paramMap.get(key).toString();
                    String[] camidarr = camids.split((","));
                    paramMap.put(key, camidarr);
                    break;
            }

        }*/
        return taskDao.queryAnalysisResult(paramMap);
    }

    /**
     * 查询人员结构化数据
     * @param paramMap
     * @return
     */
    @Override
    public List<Map<String, Object>> queryPersonFeature(Map<String, Object> paramMap){
       /* for (String key : paramMap.keySet()) {
            switch (key) {
                case "starttime":
                case "endtime":
                    break;
                default:
                    String items = paramMap.get(key).toString();
                    String[] itemarr = items.split((","));
                    paramMap.put(key, itemarr);
                    break;
            }
        }*/
        return taskDao.queryPersonFeature(paramMap);
    }

    @Override
    public List<Map<String, Object>> queryVehicle(Map<String, Object> paramMap){
        /*for (String key : paramMap.keySet()) {
            switch (key) {
                case "starttime":
                case "endtime":
                    break;
                default:
                    String items = paramMap.get(key).toString();
                    String[] itemarr = items.split((","));
                    paramMap.put(key, itemarr);
                    break;
            }
        }*/
        return taskDao.queryVehicle(paramMap);
    }

}
