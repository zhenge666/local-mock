package com.your.business.mock.facade;


import com.your.business.mock.bo.ServiceNameBO;
import com.your.business.mock.manager.ConfigManager;
import com.your.business.mock.vo.RequestMatcherVO;
import com.your.common.model.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Deyu.Chen
 * @since 2019-12-20
 */
@RestController
@RequestMapping("/mock/")
public class RequestMatcherFacade {

    @Autowired
    private ConfigManager configManager;

    /**
     * 通过服务名查询所有的配置
     *
     * @param req
     * @return
     */
    @GetMapping("/server/config")
    public Res<List<RequestMatcherVO>> queryByService(@Validated ServiceNameBO req) {
        return Res.wrapSuccessfulResult(configManager.queryByService(req.getServiceName()));
    }

}
