package com.atguigu.msm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zzy
 */
@SuppressWarnings("all")
@Component
public class MsmTencentParamsUtil implements InitializingBean {
    @Value("${com.tencentcloudapi.sms.secretId:}")
    private String secretId;

    @Value("${com.tencentcloudapi.sms.secretKey:}")
    private String secretKey;

    @Value("${com.tencentcloudapi.sms.connTimeout:60}")
    private Integer connTimeout;

    @Value("${com.tencentcloudapi.sms.endpoint:}")
    private String endpoint;

    @Value("${com.tencentcloudapi.sms.appid:}")
    private String appid;

    @Value("${com.tencentcloudapi.sms.sign:}")
    private String sign;

    @Value("${com.tencentcloudapi.sms.senderid:''}")
    private String senderid;

    @Value("${com.tencentcloudapi.sms.extendcode:''}")
    private String extendcode;

    @Value("${com.tencentcloudapi.sms.templateID:}")
    private String templateID;

    public static String SECRET_ID;
    public static String SECRET_KEY;
    public static Integer CONNTIMEOUT;
    public static String ENDPOINT;
    public static String APPID;
    public static String SIGN;
    public static String SENDER_ID;
    public static String EXTEND_CODE;
    public static String TEMPLATE_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_ID = secretId;
        SECRET_KEY = secretKey;
        CONNTIMEOUT = connTimeout;
        ENDPOINT = endpoint;
        APPID = appid;
        SIGN = sign;
        SENDER_ID = senderid;
        EXTEND_CODE = extendcode;
        TEMPLATE_ID = templateID;
    }
}
