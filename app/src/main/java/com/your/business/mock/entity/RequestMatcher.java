package com.your.business.mock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Deyu.Chen
 * @since 2019-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RequestMatcher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 递增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 1：httpclient
     */
    private Integer clientType;

    /**
     * 请求名称
     */
    private String requestName;

    /**
     * 请求方法，大写：GET、POST
     */
    private String requestMethod;

    /**
     * 请求地址全路径
     */
    private String requestUrl;

    /**
     * 请求头，key-value的json格式
     */
    private String requestHeaderJson;

    /**
     * 请求体内的参数json
     */
    private String bodyParamJson;

    /**
     * 请求体全匹配
     */
    private String bodyContent;

    /**
     * 响应状态
     */
    private Integer responseStatus;

    /**
     * 响应头json
     */
    private String responseHeaderJson;

    /**
     * 响应cookie，key-value的json
     */
    private String responseCookieJson;

    /**
     * 响应内容
     */
    private String responseContent;

    /**
     * 响应类型，默认json，还有：application/json、application/xml、text/plain、application/x-www-form-urlencoded、multipart/form-data等
     */
    private String responseContentType;

    /**
     * 0：无异常，1：抛出异常
     */
    private Integer responseException;

    /**
     * 0：关闭，1：打开
     */
    private Integer onlineStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
