package com.your.mock.httpclient.builder;

import com.your.mock.httpclient.request.RequestBodyCondition;
import com.your.mock.httpclient.request.RequestBodyParamCondition;
import com.your.mock.httpclient.request.RequestCondition;
import com.your.mock.httpclient.request.RequestHeaderCondition;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;

/**
 * mock接口生成器
 *
 * @author zhangzhen
 * @date 2019-12-18 19:22
 */
public class HttpClientMockBuilder {

    private final RuleBuilder ruleBuilder;
    private final HttpClientResponseBuilder responseBuilder;

    public HttpClientMockBuilder(RuleBuilder rule) {
        this.ruleBuilder = rule;
        this.responseBuilder = new HttpClientResponseBuilder(rule);
    }

    /**
     * 添加请求host规则
     *
     * @param host
     * @return
     */
    public HttpClientMockBuilder withHost(String host) {
        ruleBuilder.addHostCondition(host);
        return this;
    }

    /**
     * 添加请求地址规则
     *
     * @param path
     * @return
     */
    public HttpClientMockBuilder withPath(String path) {
        return withPath(equalTo(path));
    }

    private HttpClientMockBuilder withPath(Matcher<String> matcher) {
        ruleBuilder.addPathCondition(matcher);
        return this;
    }

    /**
     * 添加请求参数规则
     *
     * @param name
     * @param value
     * @return
     */
    public HttpClientMockBuilder withUrlParameter(String name, String value) {
        return withUrlParameter(name, equalTo(value));
    }

    private HttpClientMockBuilder withUrlParameter(String name, Matcher<String> matcher) {
        ruleBuilder.addParameterCondition(name, matcher);
        return this;
    }

    /**
     * 添加请求地址的reference（:url#reference）参数规则
     *
     * @param reference
     * @return
     */
    public HttpClientMockBuilder withUrlReference(String reference) {
        return withUrlReference(equalTo(reference));
    }

    private HttpClientMockBuilder withUrlReference(Matcher<String> matcher) {
        ruleBuilder.addReferenceCondition(matcher);
        return this;
    }

    /**
     * 添加请求body里的参数规则
     *
     * @param key
     * @param value
     * @return
     */
    public HttpClientMockBuilder withBodyParameter(String key, String value) {
        return withBodyParameter(key, equalTo(value));
    }

    private HttpClientMockBuilder withBodyParameter(String key, Matcher<String> matcher) {
        ruleBuilder.addCondition(new RequestBodyParamCondition(key, matcher));
        return this;
    }

    /**
     * 添加请求头规则
     *
     * @param header
     * @param value
     * @return
     */
    public HttpClientMockBuilder withHeader(String header, String value) {
        return withHeader(header, equalTo(value));
    }

    private HttpClientMockBuilder withHeader(String header, Matcher<String> matcher) {
        ruleBuilder.addCondition(new RequestHeaderCondition(header, matcher));
        return this;
    }

    /**
     * 添加自定义规则
     *
     * @param condition
     * @return
     */
    public HttpClientMockBuilder withCondition(RequestCondition condition) {
        ruleBuilder.addCondition(condition);
        return this;
    }

    /**
     * 添加请求body规则，需要内容完全一致才可匹配成功
     *
     * @param body
     * @return
     */
    public HttpClientMockBuilder withBody(String body) {
        return withBody(equalTo(body));
    }

    private HttpClientMockBuilder withBody(Matcher<String> matcher) {
        ruleBuilder.addCondition(new RequestBodyCondition(matcher));
        return this;
    }

    /**
     * 获取响应生成器
     *
     * @return
     */
    public HttpClientResponseBuilder getResponseBuilder() {
        return responseBuilder;
    }

}
