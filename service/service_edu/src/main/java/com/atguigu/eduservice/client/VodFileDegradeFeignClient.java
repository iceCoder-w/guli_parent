package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 王冰炜
 * @create 2021-05-05 1:31
 */

@Component
public class VodFileDegradeFeignClient implements VodClient{
    // 出错之后会执行
    @Override
    public R removeAliyunVideo(String videoId) {
        return R.error().message("删除视频出错了 time out");
    }

    @Override
    public R removeVideoList(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了 time out");
    }
}
