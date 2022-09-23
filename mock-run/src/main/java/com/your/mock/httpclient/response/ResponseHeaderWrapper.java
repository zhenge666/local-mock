package com.your.mock.httpclient.response;

import com.your.mock.httpclient.request.Request;
import org.apache.http.HttpResponse;

/**
 * describe:响应结果头信息包装
 *
 * @author zhangzhen
 * @date 2019-12-18 18:33
 */
public class ResponseHeaderWrapper implements ResponseWrapper {

    private final ResponseWrapper parentAction;
    private final String name;
    private final String value;

    public ResponseHeaderWrapper(ResponseWrapper parentAction, String name, String value) {
        this.parentAction = parentAction;
        this.name = name;
        this.value = value;
    }

    @Override
    public HttpResponse getResponse(Request r) throws Exception {
        HttpResponse response = parentAction.getResponse(r);
        response.addHeader(name, value);
        return response;
    }

}
