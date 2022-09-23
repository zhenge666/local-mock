package com.your.mock.httpclient.request;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.protocol.HttpContext;

/**
 * describe:请求条件匹配接口
 *
 * @author zhangzhen
 * @date 2019-12-18 18:29
 */
public interface RequestCondition {

    default boolean matches(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        return matches(new Request(httpHost, httpRequest, httpContext));
    }

    boolean matches(Request request);

}
