package com.your.business.mock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.your.business.mock.entity.RequestMatcher;
import com.your.business.mock.mapper.RequestMatcherMapper;
import com.your.business.mock.service.IRequestMatcherService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Deyu.Chen
 * @since 2019-12-20
 */
@Service
public class RequestMatcherServiceImpl extends ServiceImpl<RequestMatcherMapper, RequestMatcher> implements IRequestMatcherService {

    @Override
    public List<RequestMatcher> getRequestList(String serviceName) {
        QueryWrapper<RequestMatcher> queryWrapper = new QueryWrapper();
        queryWrapper.eq("service_name", serviceName);
        queryWrapper.eq("online_status", 1);
        return this.list(queryWrapper);
    }

}
