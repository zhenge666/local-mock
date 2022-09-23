package com.your.business.mock.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * describe:拉取配置服务
 *
 * @author zhangzhen
 * @date 2019-12-20 16:23
 */
@Data
public class ServiceNameBO implements Serializable {

    @NotEmpty(message = "服务名不可为空")
    private String serviceName;

}
