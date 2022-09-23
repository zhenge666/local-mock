package com.your.mock.httpclient.builder;

import com.your.mock.httpclient.request.RequestCondition;
import com.your.mock.httpclient.request.RequestMethodCondition;
import com.your.mock.httpclient.request.UrlConditions;
import com.your.mock.httpclient.request.UrlParser;
import com.your.mock.httpclient.response.ResponseWrapper;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

/**
 * mock规则生成器
 *
 * @author zhangzhen
 * @date 2019-12-18 19:22
 */
public class RuleBuilder {

    private final List<ResponseWrapper> actions = new ArrayList<>();
    private final List<RequestCondition> conditions = new ArrayList<>();
    private final UrlConditions urlConditions = new UrlConditions();

    public RuleBuilder(String method, String defaultHost, String url) {
        UrlParser urlParser = new UrlParser();
        if (url.startsWith("/")) {
            url = defaultHost + url;
        }
        addCondition(new RequestMethodCondition(method));
        addUrlConditions(urlParser.parse(url));
    }

    public RuleBuilder(String method) {
        addCondition(new RequestMethodCondition(method));
    }

    void addAction(ResponseWrapper o) {
        actions.add(o);
    }

    void addCondition(RequestCondition o) {
        conditions.add(o);
    }

    private void addUrlConditions(UrlConditions newUrlConditions) {
        this.urlConditions.join(newUrlConditions);
    }

    /**
     * 添加域名规则
     *
     * @param host
     */
    public void addHostCondition(String host) {
        UrlParser urlParser = new UrlParser();
        UrlConditions urlConditions = new UrlConditions();
        urlConditions.setHostConditions(urlParser.parse(host).getHostConditions());
        addUrlConditions(urlConditions);
    }

    /**
     * 添加请求地址规则
     *
     * @param matcher
     */
    public void addPathCondition(Matcher<String> matcher) {
        UrlConditions urlConditions = new UrlConditions();
        urlConditions.getPathConditions().add(matcher);
        addUrlConditions(urlConditions);
    }

    /**
     * 添加请求地址里的参数规则
     *
     * @param name
     * @param matcher
     */
    public void addParameterCondition(String name, Matcher<String> matcher) {
        UrlConditions urlConditions = new UrlConditions();
        urlConditions.getParameterConditions().put(name, matcher);
        addUrlConditions(urlConditions);
    }

    /**
     * 添加Reference规则
     *
     * @param matcher
     */
    public void addReferenceCondition(Matcher<String> matcher) {
        UrlConditions urlConditions = new UrlConditions();
        urlConditions.setReferenceConditions(matcher);
        addUrlConditions(urlConditions);
    }

    ResponseWrapper getLastAction() {
        return actions.get(actions.size() - 1);
    }

    void overrideLastAction(ResponseWrapper lastAction) {
        actions.set(actions.size() - 1, lastAction);
    }

    /**
     * 生成具体的规则条件
     *
     * @return
     */
    public Rule toRule() {
        return new Rule(urlConditions, conditions, actions);
    }

}
