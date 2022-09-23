package com.your.mock.httpclient.builder;

import com.your.mock.httpclient.response.*;
import org.apache.http.entity.ContentType;

import java.io.IOException;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.apache.http.entity.ContentType.APPLICATION_XML;

/**
 * mock响应生成器
 *
 * @author zhangzhen
 * @date 2019-12-18 19:22
 */
public class HttpClientResponseBuilder {

    private final RuleBuilder newRule;

    HttpClientResponseBuilder(RuleBuilder rule) {
        this.newRule = rule;
    }

    /**
     * 设置响应头
     *
     * @param name
     * @param value
     * @return
     */
    public HttpClientResponseBuilder withHeader(String name, String value) {
        ResponseWrapper lastAction = newRule.getLastAction();
        ResponseHeaderWrapper headerAction = new ResponseHeaderWrapper(lastAction, name, value);
        newRule.overrideLastAction(headerAction);
        return this;
    }

    /**
     * 设置响应状态码
     *
     * @param statusCode
     * @return
     */
    public HttpClientResponseBuilder withStatus(int statusCode) {
        ResponseWrapper lastAction = newRule.getLastAction();
        ResponseStatusWrapper statusAction = new ResponseStatusWrapper(lastAction, statusCode);
        newRule.overrideLastAction(statusAction);
        return this;
    }

    /**
     * 设置响应cookie
     *
     * @param cookieName
     * @param cookieValue
     * @return
     */
    public HttpClientResponseBuilder withCookie(String cookieName, String cookieValue) {
        ResponseWrapper lastAction = newRule.getLastAction();
        ResponseCookieWrapper cookieAction = new ResponseCookieWrapper(lastAction, cookieName, cookieValue);
        newRule.overrideLastAction(cookieAction);
        return this;
    }

    /**
     * 添加自定义的响应处理
     *
     * @param action
     * @return
     */
    public HttpClientResponseBuilder doAction(ResponseWrapper action) {
        newRule.addAction(action);
        return this;
    }

    /**
     * 设置文本格式的响应内容
     *
     * @param response
     * @return
     */
    public HttpClientResponseBuilder doReturn(String response) {
        return doReturn(200, response, ContentType.TEXT_PLAIN);
    }

    /**
     * 设置文本格式的响应内容，自定义状态码
     *
     * @param statusCode
     * @param response
     * @return
     */
    public HttpClientResponseBuilder doReturn(int statusCode, String response) {
        return doReturn(statusCode, response, ContentType.TEXT_PLAIN);
    }

    /**
     * 自定义响应的内容、格式和状态码
     *
     * @param statusCode  状态码
     * @param response    内容
     * @param contentType 格式
     * @return
     */
    public HttpClientResponseBuilder doReturn(int statusCode, String response, ContentType contentType) {
        newRule.addAction(new ResponseStringWrapper(statusCode, response, contentType));
        return this;
    }

    /**
     * 响应Json数据，状态码200
     *
     * @param response
     * @return
     */
    public HttpClientResponseBuilder doReturnJSON(String response) {
        return doReturn(200, response, APPLICATION_JSON);
    }

    /**
     * 响应xml数据，状态码200
     *
     * @param response
     * @return
     */
    public HttpClientResponseBuilder doReturnXML(String response) {
        return doReturn(200, response, APPLICATION_XML);
    }

    /**
     * 仅响应指定状态码和空消息
     *
     * @param statusCode
     * @return
     */
    public HttpClientResponseBuilder doReturnStatus(int statusCode) {
        newRule.addAction(new ResponseStatusWrapper(statusCode));
        return this;
    }

    /**
     * 自定义异常响应
     *
     * @param exception
     * @return
     */
    public HttpClientResponseBuilder doThrowException(IOException exception) {
        newRule.addAction(new ResponseExceptionWrapper(exception));
        return this;
    }

}
