package com.your.mock.start;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;

/**
 * describe:连接mock服务和直连服务，根据条件分发请求
 *
 * @author zhangzhen
 * @date 2019-12-18 19:41
 */
@Slf4j
public class HttpClientBridge extends CloseableHttpClient {

    /**
     * 真实的请求客户端
     */
    private CloseableHttpClient realClient;

    public HttpClientBridge(CloseableHttpClient realClient) {
        this.realClient = realClient;
    }

    @Override
    public CloseableHttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
        // 仅在测试、开发环境下开放
        try {
            if (MockOpen.isMock() && MockFactory.getInstance().getHttpClientMock() != null) {
                CloseableHttpResponse response = MockFactory.getInstance().getHttpClientMock().execute(request);
                // 如果为空表示没有匹配成功,继续直连服务
                if (response != null && response.getStatusLine().getStatusCode() != SC_NOT_FOUND) {
                    log.info("匹配mock服务成功,返回mock响应");
                    return response;
                }
            }
        } catch (Exception ex) {
            log.error("mock出错:", ex);
        }
        return realClient.execute(request);
    }

    @Override
    protected CloseableHttpResponse doExecute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public HttpParams getParams() {
        return null;
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        return null;
    }

}
