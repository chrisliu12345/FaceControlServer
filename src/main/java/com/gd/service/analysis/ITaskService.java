package com.gd.service.analysis;

import com.gd.domain.analysis.AnalysisResult;
import com.gd.domain.analysis.AnalysisRule;
import com.gd.domain.analysis.Task;
import com.gd.domain.analysis.TblAlarmLinkage;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface ITaskService {
    public List<Task> queryTaskList( Map<String,Object> paramMap);
    public int createTask(Task task);
    public int createMainTask(Task task);
    public int createTaskAnalysisRule(AnalysisRule analysisRule);
    public int changeAnalysisRuleStatus(int ruleid, int status);
    public int changeTaskStatus(int ruleid, int status);
    public int modifyTaskName(int taskid, String taskname);
    public List<AnalysisRule> queryroolList();
    public List<Map<String,Object>> queryAnalysisResult(Map<String, Object> paramMap);
    public List<Map<String, Object>> queryPersonFeature(Map<String, Object> paramMap);
    public List<Map<String, Object>> queryVehicle(Map<String, Object> paramMap);
    public List<Map<String, Object>> queryTaskGatCode(int tabletype);
    public List<TblAlarmLinkage> queryAlarmLink(Map<String, Object> paramMap);
}
