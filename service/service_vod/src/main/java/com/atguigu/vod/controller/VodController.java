package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.util.ConstantPropertiesUtil;
import com.atguigu.vod.util.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 王冰炜
 * @create 2021-05-02 17:28
 */

@Api(tags = "上传视频管理")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    // 上传视频到阿里云
    @ApiOperation("上传视频到阿里云")
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file){
        // 返回上传视频的id
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }

    // 根据视频id删除视频
    @ApiOperation("根据视频id删除视频")
    @DeleteMapping("removeAliyunVideo/{videoId}")
    public R removeAliyunVideo(@PathVariable String videoId){
        vodService.removeVideo(videoId);
        return R.ok().message("视频删除成功");
    }

    // 删除多个阿里云视频
    @ApiOperation("删除多个阿里云视频")
    @DeleteMapping("delete-batch")
    public R removeVideoList(
            @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
            @RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeVideoList(videoIdList);
        return R.ok().message("视频删除成功");
    }

    // 根据视频id获得视频的播放凭证
    @ApiOperation("根据视频id获得视频的播放凭证")
    @GetMapping("getPlayPath/{id}")
    public R getPlayPath(
            @ApiParam(name = "id", value = "云端视频id", required = true)
            @PathVariable String id) {
        try {
            // 创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            // 获得凭证
            String playAuth = response.getPlayAuth();
            return R.ok().message("获取凭证成功").data("playAuth", playAuth);
        } catch (Exception e){
            throw new GuliException(20001, "获取凭证失败");
        }
    }
}
