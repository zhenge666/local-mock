package com.your.mock.start;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.your.common.util.HttpsUtil;
import com.your.mock.httpclient.HttpClientMock;
import com.your.mock.httpclient.builder.HttpClientMockBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * describe:
 *
 * @author zhangzhen
 * @date 2019-12-20 17:02
 */
@Configuration
@Slf4j
class MockConfigRefreshTask {

    @Value("${spring.application.name}")
    private String serviceName;

    @Autowired
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    /**
     * 启动任务，拉取配置，设置本地mock对象
     */
    @PostConstruct
    public void startInit() {
        if (MockOpen.isMock()) {
            pullMockConfig();
        }
    }

    /**
     * 拉取mock配置
     */
    private void pullMockConfig() {
        try {
            String url = String.format("https://t-apicloud.ccrgt.com/crgt-test/mock-config/mock/server/config?serviceName=%s", serviceName);
            log.info("拉取mock配置数据:{}", url);
            JSONObject jsonObject = HttpsUtil.get(url);
            if (jsonObject != null && jsonObject.getString("code").equals("0")) {
                String dataJson = jsonObject.getString("data");
                if (StringUtils.isNotBlank(dataJson)) {
                    List<MockConfigVO> voList = JSONArray.parseArray(dataJson, MockConfigVO.class);
                    createHttpClientMock(voList);
                } else {
                    log.info("{},没有找到该服务mock配置数据", serviceName);
                }
            }
            // 一分钟后再次执行
            scheduledThreadPoolExecutor.schedule(() -> {
                pullMockConfig();
            }, 1, TimeUnit.MINUTES);
        } catch (Exception ex) {
            log.error("拉取mock配置数据出错:", ex);
        }
    }

    /**
     * 初始化httpClient类型
     *
     * @param voList
     */
    public void createHttpClientMock(List<MockConfigVO> voList) {
        try {
            if (CollectionUtils.isEmpty(voList)) {
                return;
            }
            HttpClientMock httpClientMock = new HttpClientMock();
            int haveCount = 0;
            for (MockConfigVO vo : voList) {
                if (vo.getClientType() == null || !vo.getClientType().equals(1)) {
                    continue;
                }
                if (StringUtils.isBlank(vo.getRequestMethod()) || StringUtils.isBlank(vo.getRequestUrl())) {
                    continue;
                }
                if (!vo.getRequestMethod().equals("GET") && !vo.getRequestMethod().equals("POST")) {
                    continue;
                }
                HttpClientMockBuilder mockBuilder = null;
                if (StringUtils.isNotBlank(vo.getRequestMethod()) && vo.getRequestMethod().equals("GET")) {
                    mockBuilder = httpClientMock.onGet(vo.getRequestUrl());
                } else if (StringUtils.isNotBlank(vo.getRequestMethod()) && vo.getRequestMethod().equals("POST")) {
                    mockBuilder = httpClientMock.onPost(vo.getRequestUrl());
                    if (StringUtils.isNotBlank(vo.getBodyContent())) {
                        mockBuilder.withBody(vo.getBodyContent());
                    }
                    if (StringUtils.isNotBlank(vo.getBodyParamJson())) {
                        JSONObject jsonObject = JSON.parseObject(vo.getBodyParamJson());
                        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                            if (entry != null && entry.getKey() != null && entry.getValue() != null) {
                                mockBuilder.withBodyParameter(entry.getKey(), entry.getValue().toString());
                            }
                        }
                    }
                }
                if (StringUtils.isNotBlank(vo.getRequestHeaderJson())) {
                    JSONObject jsonObject = JSON.parseObject(vo.getRequestHeaderJson());
                    for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                        if (entry != null && entry.getKey() != null && entry.getValue() != null) {
                            mockBuilder.withHeader(entry.getKey(), entry.getValue().toString());
                        }
                    }
                }
                // 开始包装响应数据
                if (vo.getResponseStatus() != null && StringUtils.isBlank(vo.getResponseContent())) {
                    mockBuilder.getResponseBuilder().withStatus(vo.getResponseStatus());
                }
                if (StringUtils.isNotBlank(vo.getResponseContent()) && StringUtils.isNotBlank(vo.getResponseContentType())) {
                    mockBuilder.getResponseBuilder().doReturn(vo.getResponseStatus(), vo.getResponseContent(), ContentType.create(vo.getResponseContentType(), Consts.UTF_8));
                }
                if (vo.getResponseException() != null && vo.getResponseException().equals(1)) {
                    mockBuilder.getResponseBuilder().doThrowException(new IOException("mock设置异常"));
                }
                if (StringUtils.isNotBlank(vo.getResponseCookieJson())) {
                    JSONObject jsonObject = JSON.parseObject(vo.getResponseCookieJson());
                    for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                        if (entry != null && entry.getKey() != null && entry.getValue() != null) {
                            mockBuilder.getResponseBuilder().withCookie(entry.getKey(), entry.getValue().toString());
                        }
                    }
                }
                if (StringUtils.isNotBlank(vo.getResponseHeaderJson())) {
                    JSONObject jsonObject = JSON.parseObject(vo.getResponseHeaderJson());
                    for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                        if (entry != null && entry.getKey() != null && entry.getValue() != null) {
                            mockBuilder.getResponseBuilder().withHeader(entry.getKey(), entry.getValue().toString());
                        }
                    }
                }
                haveCount++;
            }
            if (haveCount > 0) {
                httpClientMock.initRule();
                log.info("初始化新的mock规则:{}", haveCount);
                MockFactory.getInstance().setHttpClientMock(httpClientMock);
            }
        } catch (Exception ex) {
            log.error("处理httpclient配置数据出错:", ex);
        }
    }

}
