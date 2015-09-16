package com.fighthard.httpcomponent.httpclient.component.http;

public class HttpClientFactoryImpl implements HttpClientFactory {

    private Integer timeout;
    private Integer httpPoolSize;
    private Integer httpPerRoute;

    private String proxyHost;
    private Integer proxyPort;
    private static HttpClient httpClient;

    /*
     * (non-Javadoc)
     * @see com.ucmed.common.http.HttpClientFactory#getInstance()
     */
    @Override
    public HttpClient getInstance() {
        if(null == httpClient) {
            synchronized(HttpClientFactoryImpl.class) {
                if(null == httpClient) {
                    httpClient = new AsyncHttpClient("UTF-8", proxyHost,
                            proxyPort, httpPoolSize, httpPerRoute,
                            timeout);
                }
            }
        }
        return httpClient;
    }

    public int getHttpPoolSize() {
        return httpPoolSize;
    }

    public void setHttpPoolSize(int httpPoolSize) {
        this.httpPoolSize = httpPoolSize;
    }

    public int getHttpPerRoute() {
        return httpPerRoute;
    }

    public void setHttpPerRoute(int httpPerRoute) {
        this.httpPerRoute = httpPerRoute;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }


}
