package com.your.mock.start;

import com.your.mock.httpclient.HttpClientMock;

/**
 * describe:各类型mock工厂
 *
 * @author zhangzhen
 * @date 2019-12-20 16:53
 */
class MockFactory {

    private static volatile MockFactory instance;

    public HttpClientMock httpClientMock;

    public static MockFactory getInstance() {
        if (instance == null) {
            synchronized (MockFactory.class) {
                if (instance == null) {
                    instance = new MockFactory();
                }
            }
        }
        return instance;
    }

    public HttpClientMock getHttpClientMock() {
        return httpClientMock;
    }

    public void setHttpClientMock(HttpClientMock httpClientMock) {
        this.httpClientMock = httpClientMock;
    }

}
