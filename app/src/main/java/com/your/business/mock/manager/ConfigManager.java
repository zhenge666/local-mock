package com.your.business.mock.manager;

import com.your.business.mock.entity.RequestMatcher;
import com.your.business.mock.service.IRequestMatcherService;
import com.your.business.mock.vo.RequestMatcherVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * describe:
 *
 * @author zhangzhen
 * @date 2019-12-20 16:30
 */
@Service
@Slf4j
public class ConfigManager {

    @Autowired
    private IRequestMatcherService requestMatcherService;

    /**
     * 通过服务名查询配置列表
     *
     * @param serviceName
     * @return
     */
    public List<RequestMatcherVO> queryByService(String serviceName) {
        List<RequestMatcher> requestMatcherList = requestMatcherService.getRequestList(serviceName);
        List<RequestMatcherVO> voList = new ArrayList<>(50);
        if (CollectionUtils.isNotEmpty(requestMatcherList)) {
            for (RequestMatcher entity : requestMatcherList) {
                RequestMatcherVO vo = new RequestMatcherVO();
                BeanUtils.copyProperties(entity, vo);
                voList.add(vo);
            }
        }
        return voList;
    }

}
