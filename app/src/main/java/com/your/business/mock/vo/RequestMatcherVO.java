package com.your.business.mock.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * describe:请求地址配置
 *
 * @author zhangzhen
 * @date 2019-12-20 16:21
 */
@Data
public class RequestMatcherVO implements Serializable {

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

}
