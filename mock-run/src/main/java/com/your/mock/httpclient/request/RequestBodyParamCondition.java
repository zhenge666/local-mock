package com.your.mock.httpclient.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class RequestBodyParamCondition implements RequestCondition {

    private final String key;
    private final Matcher<String> value;

    public RequestBodyParamCondition(String key, Matcher<String> value) {
        this.key = key;
        this.value = value;
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
                // 转为json，找出参数
                JSONObject obj = JSON.parseObject(message);
                if (obj.containsKey(key)) {
                    return value.matches(obj.getString(key));
                }
            } else {
                return false;
            }
        } catch (IOException e) {
        }
        return false;
    }

}
