package com.your.mock.httpclient;

import com.your.mock.httpclient.builder.HttpClientMockBuilder;
import com.your.mock.httpclient.builder.Rule;
import com.your.mock.httpclient.builder.RuleBuilder;
import com.your.mock.httpclient.request.Request;
import com.your.mock.httpclient.response.MockHttpResponseProxy;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * mock入口类
 *
 * @author zhangzhen
 * @date 2019-12-18 19:03
 */
public class HttpClientMock extends CloseableHttpClient {

    private final String defaultHost;
    private final List<Rule> rules = new ArrayList<>();
    private final HttpParams params = new BasicHttpParams();
    private final List<RuleBuilder> rulesUnderConstruction = new ArrayList<>();
    private final List<HttpRequestInterceptor> requestInterceptors = new ArrayList<>();
    private final List<HttpResponseInterceptor> responseInterceptors = new ArrayList<>();

    /**
     * 默认构造方法，没有host
     */
    public HttpClientMock() {
        this.defaultHost = "";
    }

    /**
     * 空GET请求规则，具体的规则可调用withHost、withPath、withParameter等添加
     *
     * @return
     */
    public HttpClientMockBuilder onGet() {
        return newRule("GET");
    }

    /**
     * 空POST请求规则，具体的规则可调用withHost、withPath、withParameter等添加
     *
     * @return
     */
    public HttpClientMockBuilder onPost() {
        return newRule("POST");
    }

    /**
     * 带有绝对地址url的GET请求规则，会自动解析URL添加host、path、params等规则
     * httpClientMock.onGet("http://localhost/login?user=1#app") 等效于 httpClientMock.onGet("http://localhost/login").withParameter("user","1").withReference("app)
     *
     * @param url
     * @return
     */
    public HttpClientMockBuilder onGet(String url) {
        return newRule("GET", url);
    }

    /**
     * 带有绝对地址url的POST请求规则，会自动解析URL添加host、path、params等规则
     *
     * @param url
     * @return
     */
    public HttpClientMockBuilder onPost(String url) {
        return newRule("POST", url);
    }

    /**
     * 创建规则生成器
     *
     * @param method
     * @return
     */
    private HttpClientMockBuilder newRule(String method) {
        RuleBuilder r = new RuleBuilder(method);
        rulesUnderConstruction.add(r);
        return new HttpClientMockBuilder(r);
    }

    private HttpClientMockBuilder newRule(String method, String url) {
        RuleBuilder r = new RuleBuilder(method, defaultHost, url);
        rulesUnderConstruction.add(r);
        return new HttpClientMockBuilder(r);
    }

    /**
     * 将所有的规则生成器内的条件初始化为规则
     */
    public void initRule() {
        for (RuleBuilder r : rulesUnderConstruction) {
            rules.add(r.toRule());
        }
    }

    @Override
    protected CloseableHttpResponse doExecute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException {
        executeRequestInterceptors(httpRequest, httpContext);
        HttpResponse response = getHttpResponse(httpHost, httpRequest, httpContext);
        executeResponseInterceptors(httpContext, response);
        return new MockHttpResponseProxy(response);
    }

    /**
     * 执行自定义请求拦截器
     *
     * @param httpContext
     * @param response
     * @throws IOException
     */
    private void executeResponseInterceptors(HttpContext httpContext, HttpResponse response) throws IOException {
        try {
            for (HttpResponseInterceptor responseInterceptor : responseInterceptors) {
                responseInterceptor.process(response, httpContext);
            }
        } catch (HttpException e) {
            throw new IOException(e);
        }
    }

    /**
     * 获取响应
     *
     * @param httpHost
     * @param httpRequest
     * @param httpContext
     * @return
     * @throws IOException
     */
    private HttpResponse getHttpResponse(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException {
        try {
            Request request = new Request(httpHost, httpRequest, httpContext);
            Rule rule = rules.stream()
                    .filter(r -> r.matches(httpHost, httpRequest, httpContext))
                    .reduce((a, b) -> b)
                    .orElse(Rule.NOT_FOUND);
            // 如果匹配不到，不可以返回null，会报空
            if (rule == Rule.NOT_FOUND) {
                //return null;
            }
            return rule.nextResponse(request);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * 执行自定义响应拦截器
     *
     * @param httpRequest
     * @param httpContext
     * @throws IOException
     */
    private void executeRequestInterceptors(HttpRequest httpRequest, HttpContext httpContext) throws IOException {
        try {
            for (HttpRequestInterceptor requestInterceptor : requestInterceptors) {
                requestInterceptor.process(httpRequest, httpContext);
            }
        } catch (HttpException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public HttpParams getParams() {
        return params;
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        return null;
    }

    public void addRequestInterceptor(HttpRequestInterceptor requestInterceptor) {
        this.requestInterceptors.add(requestInterceptor);
    }

    public void addResponseInterceptor(HttpResponseInterceptor responseInterceptor) {
        this.responseInterceptors.add(responseInterceptor);
    }

}
