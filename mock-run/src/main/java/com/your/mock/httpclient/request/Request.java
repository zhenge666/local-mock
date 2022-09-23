package com.your.mock.httpclient.request;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.protocol.HttpContext;

/**
 * 请求对象本地包装
 *
 * @author zhangzhen
 * @date 2019-12-18 18:41
 */
public class Request {

    private final HttpHost httpHost;
    private final HttpRequest httpRequest;
    private final HttpContext httpContext;

    public Request(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {

        this.httpHost = httpHost;
        this.httpRequest = httpRequest;
        this.httpContext = httpContext;
    }

    public HttpHost getHttpHost() {
        return httpHost;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }

    public String getUri() {
        return getHttpRequest().getRequestLine().getUri();
    }

}
