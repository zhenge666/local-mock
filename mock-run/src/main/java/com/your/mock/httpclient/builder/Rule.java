package com.your.mock.httpclient.builder;

import com.your.mock.httpclient.request.Request;
import com.your.mock.httpclient.request.RequestCondition;
import com.your.mock.httpclient.request.UrlConditions;
import com.your.mock.httpclient.response.ResponseStatusWrapper;
import com.your.mock.httpclient.response.ResponseWrapper;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

/**
 * mock规则对象
 *
 * @author zhangzhen
 * @date 2019-12-18 19:22
 */
public class Rule {

    public static final Rule NOT_FOUND = new Rule(new UrlConditions(), emptyList(), notFoundAction());
    private final LinkedList<ResponseWrapper> actions;
    private final List<RequestCondition> conditions;
    private final UrlConditions urlConditions;

    public Rule(UrlConditions urlConditions, List<RequestCondition> conditions, List<ResponseWrapper> actions) {
        this.urlConditions = urlConditions;
        this.conditions = conditions;
        this.actions = new LinkedList<>(actions);
    }

    /**
     * 判断请求对象是否符合当前规则
     *
     * @param request
     * @return
     */
    public boolean matches(Request request) {
        return matches(request.getHttpHost(), request.getHttpRequest(), request.getHttpContext());
    }

    /**
     * 匹配所有规则
     *
     * @param httpHost
     * @param httpRequest
     * @param httpContext
     * @return true：表示匹配成功
     */
    public boolean matches(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        boolean checkUrl = urlConditions.matches(httpRequest.getRequestLine().getUri());
        boolean checkAll = conditions.stream().allMatch(c -> c.matches(httpHost, httpRequest, httpContext));
        if (checkUrl && checkAll) {
            return true;
        }
        return false;
    }

    /**
     * 至少一个action，没有匹配成功的默认 notFoundAction()
     *
     * @param request
     * @return
     * @throws IOException
     */
    public HttpResponse nextResponse(Request request) throws Exception {
        ResponseWrapper action;
        if (actions.size() > 1) {
            action = actions.poll();
        } else {
            action = actions.peek();
        }
        return action.getResponse(request);
    }

    /**
     * 匹配不成功的默认响应结果，返回404
     *
     * @return
     */
    private static List<ResponseWrapper> notFoundAction() {
        ArrayList<ResponseWrapper> actions = new ArrayList<>();
        actions.add(new ResponseStatusWrapper(SC_NOT_FOUND));
        return actions;
    }

}
