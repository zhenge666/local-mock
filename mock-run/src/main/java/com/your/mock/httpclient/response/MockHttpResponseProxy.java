package com.your.mock.httpclient.response;

import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.Locale;

/**
 * HttpResponseProxy的本地包装对象
 *
 * @author zhangzhen
 * @date 2019-12-18 19:03
 */
public class MockHttpResponseProxy implements CloseableHttpResponse {

    private final HttpResponse original;

    public MockHttpResponseProxy(HttpResponse original) {
        this.original = original;

    }

    public void close() throws IOException {

    }

    public StatusLine getStatusLine() {
        return this.original.getStatusLine();
    }

    public void setStatusLine(StatusLine statusline) {
        this.original.setStatusLine(statusline);
    }

    public void setStatusLine(ProtocolVersion ver, int code) {
        this.original.setStatusLine(ver, code);
    }

    public void setStatusLine(ProtocolVersion ver, int code, String reason) {
        this.original.setStatusLine(ver, code, reason);
    }

    public void setStatusCode(int code) throws IllegalStateException {
        this.original.setStatusCode(code);
    }

    public void setReasonPhrase(String reason) throws IllegalStateException {
        this.original.setReasonPhrase(reason);
    }

    public HttpEntity getEntity() {
        return this.original.getEntity();
    }

    public void setEntity(HttpEntity entity) {
        this.original.setEntity(entity);
    }

    public Locale getLocale() {
        return this.original.getLocale();
    }

    public void setLocale(Locale loc) {
        this.original.setLocale(loc);
    }

    public ProtocolVersion getProtocolVersion() {
        return this.original.getProtocolVersion();
    }

    public boolean containsHeader(String name) {
        return this.original.containsHeader(name);
    }

    public Header[] getHeaders(String name) {
        return this.original.getHeaders(name);
    }

    public Header getFirstHeader(String name) {
        return this.original.getFirstHeader(name);
    }

    public Header getLastHeader(String name) {
        return this.original.getLastHeader(name);
    }

    public Header[] getAllHeaders() {
        return this.original.getAllHeaders();
    }

    public void addHeader(Header header) {
        this.original.addHeader(header);
    }

    public void addHeader(String name, String value) {
        this.original.addHeader(name, value);
    }

    public void setHeader(Header header) {
        this.original.setHeader(header);
    }

    public void setHeader(String name, String value) {
        this.original.setHeader(name, value);
    }

    public void setHeaders(Header[] headers) {
        this.original.setHeaders(headers);
    }

    public void removeHeader(Header header) {
        this.original.removeHeader(header);
    }

    public void removeHeaders(String name) {
        this.original.removeHeaders(name);
    }

    public HeaderIterator headerIterator() {
        return this.original.headerIterator();
    }

    public HeaderIterator headerIterator(String name) {
        return this.original.headerIterator(name);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public HttpParams getParams() {
        return this.original.getParams();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setParams(HttpParams params) {
        this.original.setParams(params);
    }

    public String toString() {
        return "HttpResponseProxy{" + this.original + '}';
    }

}
