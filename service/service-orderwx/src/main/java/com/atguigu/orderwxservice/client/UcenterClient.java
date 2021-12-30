package com.atguigu.orderwxservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //根据课程id查询课程信息
    @GetMapping("/eduCenter/apimember/getInfoUc/{id}")
    public com.atguigu.commonutils.vo.UcenterMember getInfo(@PathVariable("id") String id);
}
