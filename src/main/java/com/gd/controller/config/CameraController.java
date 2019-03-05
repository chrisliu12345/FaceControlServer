package com.gd.controller.config;

import com.gd.controller.common.CameraAdd;
import com.gd.controller.common.ChannelAdd;
import com.gd.domain.config.*;
import com.gd.domain.video.Camera1;
import com.gd.domain.video.Channel;
import com.gd.domain.video.Res_Attr;
import com.gd.service.common.ICameraService;
import com.gd.service.common.IChannelService;
import com.gd.service.common.IResService;
import com.gd.service.config.IConfigService;
import com.google.gson.Gson;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.*;

/**
 * Created by Administrator on 2017/12/21 0021.
 */
@RequestMapping("/camera")
@RestController
public class CameraController {
    @Autowired
    private IConfigService configService;
    @Autowired
    private IResService resService;
    @Autowired
    private ChannelAdd channelController;
    @Autowired
    private ICameraService iCameraService;
    @Autowired
    private IChannelService channelService;
    //查询摄像机表数据
    @RequestMapping(method = RequestMethod.GET)
    public String getCamera(){
        List<Camera> cameraList=this.configService.getCamera();
        for(Camera  camera: cameraList){
            if(camera.getCamerId()==camera.getShowCameraID()){
                    camera.setTemp("默认显示");//该摄像机为默认显示的摄像机
            }else {

            }
        }
        Map<String ,Object> map=new HashMap<>();
        map.put("data",cameraList);
        map.put("code","0001");
        Gson gson=new Gson();
        return gson.toJson(map);
    }
    //更新摄像机表数据
    @RequestMapping( value = "/update", method = RequestMethod.POST)
    public String updateCamera(@RequestBody Map<String,String> map){
       Camera camera=new Camera();
       camera.setCamerId(Integer.parseInt(map.get("CamerId")));
       camera.setSite(map.get("Site").toString());
       camera.setTaskType(map.get("TaskType").toString());
       this.configService.updateCamera(camera);
       Map<String ,String> maplist=new HashMap<>();

        maplist.put("code","success");
        Gson gson=new Gson();
        return gson.toJson(maplist);
    }
    //删除摄像机前，先判断是否删除的为当前摄像机
    @RequestMapping( value = "/judgeDelete", method = RequestMethod.POST)
    public String judgeDelete(@RequestBody Map<String,String> map){
        Map<String ,String> maplist=new HashMap<>();
        Gson gson=new Gson();
        //查询config表中的值是否与CameraID相等
        String cameraid=this.configService.getCameraParam();
        String serviceIP=this.configService.queryForServicesIpByid(Integer.parseInt(map.get("ServiceId")));
        if(cameraid.equals(map.get("CamerId"))){
            maplist.put("data","yes");
            maplist.put("servip",serviceIP);
            return gson.toJson(maplist);
        }else{
            maplist.put("data","no");
            maplist.put("servip",serviceIP);
            return gson.toJson(maplist);
        }
    }
    //判断是删除当前摄像机时执行的方法
    @RequestMapping( value = "/delete1", method = RequestMethod.POST)
    public String delete1Camera(@RequestBody Map<String,String> map){
        Camera camera=new Camera();
        camera.setCamerId(Integer.parseInt(map.get("CamerId")));
        //删除摄像机表数据fr_camera
        this.configService.deleteCamera(camera);
        //删除fr_param_identify表中的数据
        this.configService.deleteForidentify(camera);
        //删除fr_param_collect表中的数据
        this.configService.deleteForcollect(camera);
        //删除fr_param_identify_contrast表中的数据
        this.configService.deleteIdentifyContrast(camera);
        //删除tbl_res_attr表中的数据
        Res_Attr res_attr=new Res_Attr();
        res_attr.setResID(Integer.parseInt(map.get("ServiceCameraID")));
        this.resService.delete(res_attr);
        //删除tbl_camera表中的数据
        Camera1 camera2=new Camera1();
        camera2.setResID(Integer.parseInt(map.get("ServiceCameraID")));
        this.iCameraService.delete(camera2);
        //删除tbl_channel表中的数据
        Channel channel=new Channel();
        channel.setNvrID(Integer.parseInt(map.get("ServiceCameraID")));
        this.channelService.delete1(channel);
        //查询当前摄像机表的第一条记录，然后将CameraID赋值给Config表中
        Camera camera1=this.configService.queryTopForCamera();
        if (camera1==null){
            camera1.setCamerId(0000);
            this.configService.updateDisplayCameraIdSetting(camera1.getCamerId());
        }
        else{
            this.configService.updateDisplayCameraIdSetting(camera1.getCamerId());
        }
        Map<String ,String> maplist=new HashMap<>();
        maplist.put("code","success");
        Gson gson=new Gson();
        return gson.toJson(maplist);
    }

    //删除摄像机数据
    @RequestMapping( value = "/delete", method = RequestMethod.POST)
    public String deleteCamera(@RequestBody Map<String,String> map){
        Camera camera=new Camera();
        camera.setCamerId(Integer.parseInt(map.get("CamerId")));
        camera.setServiceCameraID(map.get("ServiceCameraID"));
        String ServicesIp=this.configService.queryForServicesIpByid(Integer.parseInt(map.get("ServiceId")));
        //删除摄像机表数据fr_camera
        this.configService.deleteCamera(camera);
         //删除tbl_res_attr表中的数据
        Res_Attr res_attr=new Res_Attr();
        res_attr.setResID(Integer.parseInt(map.get("ServiceCameraID")));
        this.resService.delete(res_attr);
        //删除tbl_camera表中的数据
        Camera1 camera1=new Camera1();
        camera1.setResID(Integer.parseInt(map.get("ServiceCameraID")));
        this.iCameraService.delete(camera1);
        //删除tbl_channel表中的数据
        Channel channel=new Channel();
        channel.setNvrID(Integer.parseInt(map.get("ServiceCameraID")));
        this.channelService.delete1(channel);
        Map<String ,String> mapData=new HashMap<>();
        mapData.put("code","success");
        mapData.put("data1",ServicesIp);
        Gson gson=new Gson();
        return gson.toJson(mapData);
    }
    //添加摄像机
    @RequestMapping( value = "/add", method = RequestMethod.POST)
    public String addCamera(@RequestBody Map<String,String> map){
        String coCount = this.configService.queryForTableExist();
        if (coCount.equals("access")) {
        String site=map.get("Site");//位置名称
        String TaskType=map.get("TaskType");//任务类型
        String url=map.get("url");//Ip地址
        String location=map.get("location");//考勤地点
        String username=map.get("username");//用户名
        String password=map.get("password");//密码
        String Port=map.get("Port");//端口号
        String CsServicesId=map.get("ServiceIP");
        String ProtocolType=map.get("ProtocolType");//摄像机类型
        int StreamingID=2;  //转发服务ID
        //第一步，先拼接url。
        String resultURL="rtsp://"+username+":"+password+"@"+url+"/main/av_stream";
        //第二步向tbl_res_attr表，camera表，channel表中插入数据
        CameraAdd cameraServer= null;
        Res_Attr res_attr= null;
        Map<String,String> map1=new HashMap<>();
        map1.put("Name",site);
        map1.put("IPAddress",url);
        map1.put("UsrName",username);
        map1.put("Password",password);
        map1.put("StreamingID",String.valueOf(StreamingID));
        map1.put("ResType","132");
        map1.put("ProtocolType",ProtocolType);
        map1.put("Port",Port);
        cameraServer = new CameraAdd();
        res_attr = cameraServer.add(map1);
        this.resService.add(res_attr);
        String  cameraName= ProtocolType;
        Integer cameraType =132;
        Integer resid=this.resService.queryforResId(res_attr);
        Map<String ,String> map2=new HashMap<>();
        map2.put("Alias",site);
        map2.put("ResID",String.valueOf(resid));
        map2.put("StreamId","5");
        Camera1 camera1=cameraServer.addCam(map2);
         this.iCameraService.add(camera1);
        //IPC摄像机则只加一个通道
        this.channelController.addChannel(1 ,cameraType,cameraName,resid);
        /*//添加录像计划
        SaveConfig saveConfig = new SaveConfig();
        saveConfig.setCamID(resid);
        saveConfig.setStreamingID(2);
        saveConfig.setStartTime(Time.valueOf("00:00:00"));
        saveConfig.setStopTime(Time.valueOf("23:59:00"));
        saveConfig.setWorkDay(127);
        saveConfig.setKeepTime(1);
        saveConfig.setIslostStop(0);
        this.iCameraService.addStorPlan(saveConfig);*/
        //第三步向fr_camera表，fr_param_identify表，fr_param_collect表,fr_param_model_deepinsight表插入信息
        Camera camera=new Camera();
        String uuid= UUID.randomUUID().toString();
        camera.setServiceCameraID(String.valueOf(resid));
        camera.setSite(map.get("Site"));
        camera.setTaskType(map.get("TaskType"));
        camera.setUrl(resultURL);
        camera.setAttendanceLocationID(map.get("location"));
        camera.setServiceId(CsServicesId);
        camera.setFeatureID(1);
        camera.setCamConfigID(1);
        this.configService.addCamera(camera);
        //获取摄像机ID
        Integer camid=camera.getCamerId();
        //获取CsServices表对应的ServiceIP地址
        String ServicesIp=this.configService.queryForServicesIpByid(Integer.parseInt(CsServicesId));

            Map<String, Object> map3 = new HashMap<>();
            map3.put("code", "200");
            map3.put("data1", camid);
            map3.put("data2", ServicesIp);
            Gson gson = new Gson();
            return gson.toJson(map3);
        } else {
            Map<String, Object> map3 = new HashMap<>();
            System.out.println(coCount);
            map3.put("code", coCount);
            Gson gson = new Gson();
            return gson.toJson(map3);
        }
    };
    //获取视频联网平台摄像机数据
    @RequestMapping( value = "/tblCamera", method = RequestMethod.GET)
    public String getTblCamera() {
        Map<String, Object> maps = new HashMap<>();
        Gson gson = new Gson();
        //获取联网平台的设备数据
       List<Res_Attr> resAttrList1=this.resService.getVideoCameraTbl();
        System.out.println("视频联网平台数据源"+resAttrList1.size());
      //获取人脸布控系统设备数据
        List<Res_Attr> resAttrList2=this.resService.getVideoCameraTbl2();
        System.out.println("人脸布控系统设备数据"+resAttrList2.size());
        if(resAttrList2.size()<=0){
            maps.put("data", resAttrList1);
            return gson.toJson(maps);
        }else {
            List<Res_Attr> compresAttr = new ArrayList<>();
            for (int i = 0; i < resAttrList1.size(); i++) {
                for (int j = 0; j < resAttrList2.size(); j++) {
                    if (resAttrList1.get(i).getIPAddress().equals(resAttrList2.get(j).getIPAddress())) {
                        compresAttr.add(resAttrList1.get(i));
                    }
                }
            }
            System.out.println("我是集合结果:"+compresAttr);
            resAttrList1.removeAll(compresAttr);
            maps.put("data", resAttrList1);
            return gson.toJson(maps);
        }
    }
    @RequestMapping( value = "/addtbl", method = RequestMethod.POST)
    public String addTblCamera(@RequestBody Map<String,Object> map) {
        Map<String, Object> maps = new HashMap<>();
        Gson gson = new Gson();
        //先判断人脸布控系统的数据库里是否有此摄像机设备，如果有了，则返回，不能重复加入，如果没有，则可以加入。
        int t=this.resService.searchForResAttrByIP(map.get("IPAddress").toString());
        if(t>0){
            maps.put("info",1);
            return gson.toJson(maps);
        }else {
            //先把加入到人脸布控系统的tbl_resattr、tbl_channel、tbl_camera，用生成的ResID再加入到fr_camera中
            Res_Attr res_attr=this.resService.getResAttrOne(Integer.parseInt(map.get("ResID").toString()));
            Camera1 camera1=this.iCameraService.getCameraOne(Integer.parseInt(map.get("ResID").toString()));
            Channel channel=this.channelService.getChannelOne(Integer.parseInt(map.get("ResID").toString()));
            res_attr.setSipServiceID(2);
            //删除所有实体的id,避免再新的数据库中发生冲突
            this.resService.add(res_attr);
            System.out.println("实体属性值:"+res_attr.getResID());
            Integer id=res_attr.getResID();
            camera1.setResID(id);
            this.iCameraService.add(camera1);
            channel.setChannelID(0);
            channel.setCamID(id);
            channel.setNvrID(id);
            this.channelService.addForObject(channel);
            //加入到人脸布控系统fr_camera中
            String ipAddress = this.resService.queryFortblServiceByIPAddress();
            Camera camera = new Camera();
            camera.setSite(map.get("name").toString());
            camera.setTaskType("1");
            camera.setServiceId("1");
            String url = "rtsp://" + ipAddress + ":554/realvideo/h264?cameraID=" + res_attr.getResID();
            camera.setUrl(url);
            camera.setServiceCameraID(res_attr.getResID().toString());
            camera.setAttendanceLocationID("1");
            camera.setFeatureID(1);
            camera.setCamConfigID(1);
            this.configService.addCamera(camera);

            maps.put("code", 200);
            maps.put("data", camera.getCamerId());
            return gson.toJson(maps);
        }
    }
    @RequestMapping( value = "/deletetbl", method = RequestMethod.POST)
    public String deleteTblCamera(@RequestBody String resId) {
        //删除tbl_res_attr表中的数据
        Res_Attr res_attr=new Res_Attr();
        res_attr.setResID(Integer.parseInt(resId));
        this.resService.delete(res_attr);
        //删除tbl_camera表中的数据
        Camera1 camera1=new Camera1();
        camera1.setResID(Integer.parseInt(resId));
        this.iCameraService.delete(camera1);
        //删除tbl_channel表中的数据
        Channel channel=new Channel();
        channel.setNvrID(Integer.parseInt(resId));
        this.channelService.delete1(channel);
        //删除tbl_storplan表中的数据
        SaveConfig saveConfig=new SaveConfig();
        saveConfig.setCamID(Integer.parseInt(resId));
        this.iCameraService.deleteStoreplan(saveConfig);
        Map<String, Object> maps = new HashMap<>();
        maps.put("code", 200);
        Gson gson = new Gson();
        return gson.toJson(maps);
    }

}
