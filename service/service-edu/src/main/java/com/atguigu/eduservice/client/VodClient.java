package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)//指定调用的服务名，前提要注册到nacos注册中心中
public interface VodClient {
    //根据视频id删除阿里云视频
    @DeleteMapping("/eduVod/video/removeAliyunVideoById/{id}")
    R removeAliyunVideoById(@PathVariable("id") String id);

    @DeleteMapping(value = "/eduVod/video/delete-batch")
    R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
