import com.alibaba.fastjson.JSONObject;
import com.your.mock.httpclient.HttpClientMock;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

/**
 * describe:
 *
 * @author zhangzhen
 * @date 2019-12-19 16:08
 */
public class TestHttpClientMock {

    public static void main(String[] args) {
        try {
            HttpClientMock httpClientMock = new HttpClientMock();

            httpClientMock.onGet().withHost("https://test.cn").withPath("/operatemonit/wifiapps/oauth2/grant").getResponseBuilder().doReturnJSON("{\"data\":{\"token\":\"11\"},\"status\":0}");
            httpClientMock.onGet().withHost("https://test.cn").withPath("/operatemonit/wifiapps/oauth2/grant").withUrlParameter("secret", "eKv").getResponseBuilder().doReturnJSON("{\"data\":{\"token\":\"222\"},\"status\":0}");
            httpClientMock.onGet("https://test.cn/operatemonit/wifiapps/oauth2/grant?appid=4_LmvfCLHV&secret=eKv").getResponseBuilder().doReturnJSON("{\"data\":{\"token\":\"333\"},\"status\":0}");
            httpClientMock.onPost().withHost("https://test.cn").withPath("/operatemonit/wifiapps/oauth2/grant").withBodyParameter("secret", "eKv").getResponseBuilder().doReturnJSON("{\"data\":{\"token\":\"444\"},\"status\":0}");
            httpClientMock.initRule();

            HttpGet request = new HttpGet("https://test.cn/operatemonit/wifiapps/oauth2/grant?appid=1&secret=1");
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000).build();
            request.setConfig(requestConfig);
            CloseableHttpResponse jsonResponse = httpClientMock.execute(request);
            System.out.println(jsonResponse.toString());
            System.out.println(EntityUtils.toString(jsonResponse.getEntity()));

            HttpPost request2 = new HttpPost("https://test.cn/operatemonit/wifiapps/oauth2/grant");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("secret", "1");
            StringEntity entity2 = new StringEntity(jsonObject.toString(), "UTF-8");
            entity2.setContentType("application/json");
            request2.setEntity(entity2);
            CloseableHttpResponse jsonResponse2 = httpClientMock.execute(request2);
            System.out.println(EntityUtils.toString(jsonResponse2.getEntity()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
