package com.your.mock.httpclient.response;

import com.your.mock.httpclient.request.Request;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * describe:响应结果cookie包装
 *
 * @author zhangzhen
 * @date 2019-12-18 18:33
 */
public class ResponseCookieWrapper implements ResponseWrapper {

    private final ResponseWrapper parentAction;
    private final String cookieName;
    private final String cookieValue;

    public ResponseCookieWrapper(ResponseWrapper parentAction, String cookieName, String cookieValue) {
        this.parentAction = parentAction;
        this.cookieName = cookieName;
        this.cookieValue = cookieValue;
    }

    @Override
    public HttpResponse getResponse(Request request) throws Exception {
        HttpResponse response = parentAction.getResponse(request);

        if (request.getHttpContext() == null) {
            throw new RuntimeException("No Http context");
        }
        if (!(request.getHttpContext() instanceof HttpClientContext)) {
            throw new RuntimeException("Http context is not a HttpClientContext instance.");
        }
        HttpClientContext httpClientContext = (HttpClientContext) request.getHttpContext();
        if (httpClientContext.getCookieStore() == null) {
            httpClientContext.setCookieStore(new BasicCookieStore());
        }
        httpClientContext.getCookieStore().addCookie(new BasicClientCookie(cookieName, cookieValue));
        return response;
    }

}
