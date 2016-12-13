package com.anticaptcha;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class AntigateHttpRequest {
    private String url;
    private String postRaw;
    private Integer timeout = 60_000; // milliseconds
    private Integer maxBodySize = 0; // 0 = unlimited, in bytes
    private boolean followRedirects = true; // does not work now due to moving from JSOUP to ApacheHttpClient
    private boolean validateTLSCertificates = false;
    private Map<String, String> proxy = null; //new HashMap<String, String>() {{put("host", "192.168.0.168"); put("port", "8888");}};
    private Map<String, String> cookies = new HashMap<>();
    private Map<String, String> headers = new HashMap<String, String>()
    {{
        put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        put("Accept-Encoding", "gzip, deflate, sdch");
        put("Accept-Language", "ru-RU,en;q=0.8,ru;q=0.6");
    }};

    AntigateHttpRequest(String url) {
        this.url = url;
    }

    boolean isValidateTLSCertificates() {
        return validateTLSCertificates;
    }

    String getUrl() {
        return url;
    }

    String getRawPost() {
        return postRaw;
    }

    Map<String, String> getProxy() {
        return proxy;
    }

    Integer getTimeout() {
        return timeout;
    }

    public String getReferer() {

        if (headers.get("Referer") != null) {
            return headers.get("Referer");
        }

        return null;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public Integer getMaxBodySize() {
        return maxBodySize;
    }

    public void setRawPost(String post) {
        this.postRaw = post;
    }

    public void addToPost(String key, String value) throws UnsupportedEncodingException {
        if (postRaw == null) {
            postRaw = "";
        } else {
            postRaw += "&";
        }

        postRaw += URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
        addHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }
}