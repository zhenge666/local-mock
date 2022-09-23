package com.your.business.mock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.your.business.mock.entity.RequestMatcher;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Deyu.Chen
 * @since 2019-12-20
 */
public interface IRequestMatcherService extends IService<RequestMatcher> {

    /**
     * 通过服务名查询配置列表
     *
     * @param serviceName
     * @return
     */
    List<RequestMatcher> getRequestList(String serviceName);

}
