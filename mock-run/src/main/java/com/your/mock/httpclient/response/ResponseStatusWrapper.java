package com.your.mock.httpclient.response;

import com.your.mock.httpclient.request.Request;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;

import java.util.Optional;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;

/**
 * describe:响应结果状态码包装
 *
 * @author zhangzhen
 * @date 2019-12-18 18:33
 */
public class ResponseStatusWrapper implements ResponseWrapper {

    private final Optional<ResponseWrapper> parentAction;
    private final int status;

    public ResponseStatusWrapper(int status) {
        this.status = status;
        this.parentAction = Optional.empty();
    }

    public ResponseStatusWrapper(ResponseWrapper parentAction, int status) {
        this.status = status;
        this.parentAction = Optional.of(parentAction);
    }

    @Override
    public HttpResponse getResponse(Request request) throws Exception {
        HttpResponse response;
        if (parentAction.isPresent()) {
            response = parentAction.get().getResponse(request);
        } else {
            response = new BasicHttpResponse(new ProtocolVersion("http", 1, 1), status, "");
            if (status != SC_NO_CONTENT) {
                response.setEntity(new StringEntity(""));
            }
        }
        response.setStatusCode(status);
        return response;
    }

}
