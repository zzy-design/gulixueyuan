package com.atguigu.msm.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SendTencentMsmUtil {
    public static Map<String, Object> sendTencentMsm(String PhoneNumber, String templateCode) {
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "error");
        resultMap.put("message", "send fail");
        try {
            Credential cred = new Credential(MsmTencentParamsUtil.SECRET_ID, MsmTencentParamsUtil.SECRET_KEY);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(MsmTencentParamsUtil.CONNTIMEOUT);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "", clientProfile);
            SendSmsRequest req = new SendSmsRequest();

            String appid = MsmTencentParamsUtil.APPID;
            req.setSmsSdkAppid(appid);

            String sign = MsmTencentParamsUtil.SIGN;
            req.setSign(sign);

            /* 国际/港澳台短信 senderid: 国内短信填空，默认未开通，如需开通请联系 [sms helper] */
            String senderid = MsmTencentParamsUtil.SENDER_ID;
            req.setSenderId(senderid);

            /* 用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
            String session = "";
            req.setSessionContext(session);

            /* 短信码号扩展号: 默认未开通，如需开通请联系 [sms helper] */
            String extendcode = MsmTencentParamsUtil.EXTEND_CODE;
            req.setExtendCode(extendcode);

            String templateID = MsmTencentParamsUtil.TEMPLATE_ID;
            req.setTemplateID(templateID);

            /* 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号]
             * 例如+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号*/
            String[] phoneNumbers = {"+86" + PhoneNumber};
            req.setPhoneNumberSet(phoneNumbers);

            /* 模板参数: 若无模板参数，则设置为空*/
            //RandomUtil.randomNumbers(4) hutool工具生成四位随机码
            String[] templateParams = {templateCode};
            req.setTemplateParamSet(templateParams);

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(req);

            // 输出 JSON 格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(res));

            HashMap<String, String> resMap = new HashMap<>();
            res.toMap(resMap, "");
            resultMap.put("code", resMap.get("SendStatusSet.0.Code"));
            resultMap.put("message", resMap.get("SendStatusSet.0.Message"));
            return resultMap;
        } catch (TencentCloudSDKException e) {
            log.error("发送验证码错误：{}", e);
        }
        return resultMap;
    }
}
