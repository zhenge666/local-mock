package com.your.mock.httpclient.request;

import org.apache.http.NameValuePair;
import org.hamcrest.Matchers;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;

/**
 * 请求地址解析器
 *
 * @author zhangzhen
 * @date 2019-12-18 18:45
 */
public class UrlParser {

    public static final int EMPTY_PORT_NUMBER = -1;

    /**
     * 解析地址的各个属性
     *
     * @param urlText
     * @return
     */
    public UrlConditions parse(String urlText) {
        try {
            UrlConditions conditions = new UrlConditions();
            URL url = new URL(urlText);
            if (url.getRef() != null) {
                conditions.setReferenceConditions(equalTo(url.getRef()));
            } else {
                conditions.setReferenceConditions(isEmptyOrNullString());
            }
            conditions.setSchemaConditions(Matchers.equalTo(url.getProtocol()));
            conditions.getHostConditions().add(equalTo(url.getHost()));
            conditions.getPortConditions().add(equalTo(url.getPort()));
            conditions.getPathConditions().add(equalTo(url.getPath()));
            List<NameValuePair> params = UrlParams.parse(url.getQuery(), StandardCharsets.UTF_8);
            for (NameValuePair param : params) {
                conditions.getParameterConditions().put(param.getName(), equalTo(param.getValue()));
            }
            return conditions;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
