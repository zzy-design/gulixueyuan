package com.atguigu.educenter.service.impl;

import cn.hutool.core.date.DateUtil;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 张智洋
 * @since 2021-12-16
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    private final Logger LOGGER = LoggerFactory.getLogger(UcenterMemberServiceImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(@NotNull LoginVo loginVo) {
        LOGGER.info("{}正在登录中...", loginVo.getMobile());
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验参数
        if (StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(mobile)) {
            LOGGER.info("{}登录失败...", loginVo.getMobile());
            throw new GuliException(20001, "error");
        }

        //获取会员
        UcenterMember member = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (null == member) {
            LOGGER.info("{}登录失败...帐号或密码错误", loginVo.getMobile());
            throw new GuliException(20001, "帐号或密码错误，请重新输入");
        }

        //校验密码
        if (!MD5.encrypt(password).equals(member.getPassword())) {
            LOGGER.info("{}登录失败...帐号或密码错误", loginVo.getMobile());
            throw new GuliException(20001, "帐号或密码错误，请重新输入");
        }

        //校验是否被禁用
        if (member.getIsDisabled()) {
            LOGGER.info("{}登录失败...该用户已被禁用", loginVo.getMobile());
            throw new GuliException(20001, "该用户已被禁用");
        }

        LOGGER.info("{}登录成功...", loginVo.getMobile());

        //使用JWT生成token字符串
        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if (StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "error");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(mobleCode)) {
            throw new GuliException(20001, "error");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (count > 0) {
            throw new GuliException(20001, "error");
        }

        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.save(member);
    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        return baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("openid", openid));
    }

    @Override
    public Integer countRegisterByDay(String day) {
        QueryWrapper queryWrapper = new QueryWrapper<UcenterMember>();
        queryWrapper.gt("gmt_create", DateUtil.beginOfDay(DateUtil.parseDate(day)));
        queryWrapper.lt("gmt_create", DateUtil.endOfDay(DateUtil.parseDate(day)));
        int total = baseMapper.selectCount(queryWrapper);
//        int total = baseMapper.selectRegisterCount(day);
        return total;
    }
}