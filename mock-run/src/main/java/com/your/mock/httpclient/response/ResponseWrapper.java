package com.your.mock.httpclient.response;

import com.your.mock.httpclient.request.Request;
import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * describe:响应结果包装接口，实现类对HttpResponse的属性包装
 *
 * @author zhangzhen
 * @date 2019-12-18 17:33
 */
public interface ResponseWrapper {

    /**
     * 包装HttpResponse
     *
     * @param request
     * @return
     * @throws IOException
     */
    HttpResponse getResponse(Request request) throws Exception;

}
