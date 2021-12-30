package com.atguigu.eduservice.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAliyunVideoById(String videoId) {
        return R.error().message("删除视频失败");
    }

    @Override
    public R removeVideoList(List videoIdList) {
        return R.error().message("批量删除视频失败");
    }
}
