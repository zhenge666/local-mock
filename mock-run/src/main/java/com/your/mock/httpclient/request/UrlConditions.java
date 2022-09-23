package com.your.mock.httpclient.request;

import com.your.mock.httpclient.matchers.MatchersList;
import com.your.mock.httpclient.matchers.MatchersMap;
import org.apache.http.NameValuePair;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 请求地址条件匹配器
 *
 * @author zhangzhen
 * @date 2019-12-18 18:41
 */
public class UrlConditions {

    private static final int EMPTY_PORT = -1;
    private MatchersMap<String, String> parameterConditions = new MatchersMap<>();
    private Matcher<String> referenceConditions = Matchers.isEmptyOrNullString();
    private MatchersList<String> hostConditions = new MatchersList<>();
    private MatchersList<String> pathConditions = new MatchersList<>();
    private MatchersList<Integer> portConditions = new MatchersList<>();
    private Matcher<String> schemaConditions = Matchers.any(String.class);

    public MatchersMap<String, String> getParameterConditions() {
        return parameterConditions;
    }

    public Matcher<String> getReferenceConditions() {
        return referenceConditions;
    }

    public void setReferenceConditions(Matcher<String> referenceConditions) {
        this.referenceConditions = referenceConditions;
    }

    public MatchersList<String> getHostConditions() {
        return hostConditions;
    }

    public void setHostConditions(MatchersList<String> hostConditions) {
        this.hostConditions = hostConditions;
    }

    public MatchersList<String> getPathConditions() {
        return pathConditions;
    }

    public MatchersList<Integer> getPortConditions() {
        return portConditions;
    }

    public void setSchemaConditions(Matcher<String> schemaConditions) {
        this.schemaConditions = schemaConditions;
    }

    public boolean matches(String urlText) {
        try {
            URL url = new URL(urlText);
            return hostConditions.allMatches(url.getHost())
                    && pathConditions.allMatches(url.getPath())
                    && portConditions.allMatches(url.getPort())
                    && referenceConditions.matches(url.getRef())
                    && schemaConditions.matches(url.getProtocol())
//                    && allDefinedParamsOccurredInURL(url.getQuery())
                    && allParamsHaveMatchingValue(url.getQuery());
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * 匹配url里的请求参数，注意：如果没有指定匹配的参数，返回true
     *
     * @param query
     * @return
     */
    private boolean allParamsHaveMatchingValue(String query) {
        if (parameterConditions.size() == 0) {
            return true;
        }
        UrlParams params = UrlParams.parse(query);
        // 如果有一个参数匹配上，也返回true
        for (NameValuePair param : params) {
            boolean check = parameterConditions.matches(param.getName(), param.getValue());
            if (check) {
                return true;
            }
        }
        return false;
    }

//    private boolean allDefinedParamsOccurredInURL(String query) {
//        return findMissingParameters(query).isEmpty();
//    }
//
//    private Set<String> findMissingParameters(String query) {
//        UrlParams params = UrlParams.parse(query);
//        return parameterConditions.keySet().stream()
//                .filter(((Predicate<String>) params::contain).negate())
//                .collect(Collectors.toSet());
//    }

    public void join(UrlConditions a) {
        this.referenceConditions = a.referenceConditions;
        this.schemaConditions = a.schemaConditions;
        this.portConditions.addAll(a.portConditions);
        this.pathConditions.addAll(a.pathConditions);
        this.hostConditions.addAll(a.hostConditions);
        for (String paramName : a.parameterConditions.keySet()) {
            for (Matcher<String> paramValue : a.parameterConditions.get(paramName)) {
                this.parameterConditions.put(paramName, paramValue);
            }
        }
    }

}
