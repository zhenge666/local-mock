package com.your.mock.httpclient.response;

import com.your.mock.httpclient.request.Request;
import org.apache.http.HttpResponse;

/**
 * describe:响应结果异常包装
 *
 * @author zhangzhen
 * @date 2019-12-18 18:33
 */
public class ResponseExceptionWrapper implements ResponseWrapper {

    private final Exception exception;

    public ResponseExceptionWrapper(Exception e) {
        this.exception = e;
    }

    @Override
    public HttpResponse getResponse(Request request) throws Exception {
        throw exception;
    }

}
