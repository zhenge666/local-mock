package com.your.mock.httpclient.request;

import org.hamcrest.Matcher;

/**
 * describe:请求头匹配
 *
 * @author zhangzhen
 * @date 2019-12-18 18:30
 */
public class RequestHeaderCondition implements RequestCondition {

    private final String header;
    private final Matcher<String> value;

    public RequestHeaderCondition(String header, Matcher<String> value) {
        this.header = header;
        this.value = value;
    }

    @Override
    public boolean matches(Request request) {
        return request.getHttpRequest().getFirstHeader(header) != null &&
                value.matches(request.getHttpRequest().getFirstHeader(header).getValue());
    }

}
