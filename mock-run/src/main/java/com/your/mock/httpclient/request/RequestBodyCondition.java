package com.your.mock.httpclient.request;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matcher;

import java.io.IOException;

/**
 * describe:请求body匹配
 *
 * @author zhangzhen
 * @date 2019-12-18 18:30
 */
public class RequestBodyCondition implements RequestCondition {

    private final Matcher<String> matcher;

    public RequestBodyCondition(Matcher<String> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Request request) {
        try {
            HttpRequest httpRequest = request.getHttpRequest();
            if (httpRequest instanceof HttpEntityEnclosingRequest) {
                HttpEntity entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
                if (entity == null) {
                    return false;
                }
                String message = EntityUtils.toString(entity);

                return matcher.matches(message);
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

}
