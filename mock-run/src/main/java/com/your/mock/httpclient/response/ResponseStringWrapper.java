package com.your.mock.httpclient.response;

import com.your.mock.httpclient.request.Request;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * describe:响应结果字符内容包装
 *
 * @author zhangzhen
 * @date 2019-12-18 18:33
 */
public class ResponseStringWrapper implements ResponseWrapper {

    private final int statusCode;
    private final String response;
    private final Charset charset;
    private final ContentType contentType;

    public ResponseStringWrapper(Integer statusCode, String response, ContentType contentType) {
        // 默认200
        if (statusCode == null) {
            statusCode = 200;
        }
        this.statusCode = statusCode;
        this.response = response;
        this.charset = StandardCharsets.UTF_8;
        this.contentType = contentType;
    }

    @Override
    public HttpResponse getResponse(Request request) {
        BasicHttpResponse response = new BasicHttpResponse(new ProtocolVersion("http", 1, 1), statusCode, "ok");
        StringEntity entity = new StringEntity(this.response, this.charset);
        entity.setContentType(contentType.toString());
        response.setEntity(entity);
        response.addHeader("Content-type", contentType.toString());
        return response;
    }

}
