package com.your.mock.httpclient.request;

/**
 * describe:请求方法匹配
 *
 * @author zhangzhen
 * @date 2019-12-18 18:30
 */
public class RequestMethodCondition implements RequestCondition {

    private final String method;

    public RequestMethodCondition(String method) {
        this.method = method;
    }

    @Override
    public boolean matches(Request request) {
        boolean check = request.getHttpRequest().getRequestLine().getMethod().equals(method);
        return check;
    }

}
