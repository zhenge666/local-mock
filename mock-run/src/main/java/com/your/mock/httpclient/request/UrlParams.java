package com.your.mock.httpclient.request;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 请求地址参数匹配器
 *
 * @author zhangzhen
 * @date 2019-12-18 18:41
 */
public class UrlParams extends ArrayList<NameValuePair> {

    public static UrlParams parse(String query) {
        return parse(query, StandardCharsets.UTF_8);
    }

    public static UrlParams parse(String query, Charset charset) {
        if (query == null) {
            return new UrlParams();
        } else {
            UrlParams urlParams = new UrlParams();
            urlParams.addAll(URLEncodedUtils.parse(query, charset));
            return urlParams;
        }
    }

    boolean contain(String name) {
        return stream().anyMatch(p -> p.getName().equals(name));
    }

}
