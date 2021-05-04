package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author 王冰炜
 * @since 2021-04-22
 */

@Api(tags = "小节管理")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService videoService;
    // 注入VodClient
    @Autowired
    VodClient vodClient;

    // 添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = videoService.save(eduVideo);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    // 删除小节
    // 删除小节时将连带删除对应的视频
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        // 先根据小节id获取视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        // 判断小节里是否有视频
        if (!StringUtils.isEmpty(videoSourceId)){
            // 根据视频id，远程调用实现视频删除
            vodClient.removeAliyunVideo(videoSourceId);
        }

        boolean result = videoService.removeById(id);
        if (result){
            return R.ok();
        }else {
            return R.error();
        }
    }

    // 根据id查询小节
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return R.ok().data("video",video);
    }

    // 修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean update = videoService.updateById(eduVideo);
        if (update){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

