package com.atguigu.commentservice.client;

import com.atguigu.commentservice.client.impl.UcenterClientImpl;
import com.atguigu.commonutils.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    //根据用户id获取用户信息
    @GetMapping("/eduCenter/apimember/getInfoUc/{memberId}")
    UcenterMember getMemberInfoById(@PathVariable("memberId") String memberId);
}

