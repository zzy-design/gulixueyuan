package com.atguigu.commentservice.client.impl;

import com.atguigu.commentservice.client.UcenterClient;
import com.atguigu.commonutils.vo.UcenterMember;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public UcenterMember getMemberInfoById(String memberId) {
        return null;
    }
}