package com.fighthard.httpcomponent.httpclient.component.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpRequestFutureTask;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fighthard.httpcomponent.httpclient.component.http.responsehandler.JSONResponseHandler;

public class AsyncHttpClient implements HttpClient {

    private static final Logger LOG = LoggerFactory
            .getLogger(AsyncHttpClient.class);

    private String charset = "UTF-8";
    private Integer maxPoolSize = 200;
    private Integer perRouteSize = 20;
    private Integer timeout = 10 * 1000;
    private FutureRequestExecutionService futureRequestExecutionService;

    protected AsyncHttpClient(String charset, String proxyHost,
            Integer proxyPort, Integer maxPoolSize, Integer perRouteSize,
            Integer timeout) {
        if(null != maxPoolSize && 0 < maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }
        if(null != perRouteSize && 0 < perRouteSize) {
            this.perRouteSize = perRouteSize;
        }
        if(null != timeout && 0 < timeout) {
            this.timeout = timeout;
        }
        if(StringUtils.isNotBlank(charset)) {
            this.charset = StringUtils.trim(charset);
        }

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(this.perRouteSize);
        if(StringUtils.isNotBlank(proxyHost)) {
            if(null == proxyPort || 0 > proxyPort) {
                proxyPort = 80;
            }
            HttpHost proxy = new HttpHost(StringUtils.trim(proxyHost),
                    proxyPort);
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(
                    proxy);
            httpClientBuilder.setRoutePlanner(routePlanner);
        }

        org.apache.http.client.HttpClient httpClient = httpClientBuilder
                .build();
        ExecutorService executorService = Executors
                .newFixedThreadPool(this.maxPoolSize);
        futureRequestExecutionService = new FutureRequestExecutionService(
                httpClient, executorService);
    }

    @Override
    public JSONObject getAsJSON(URL url, Map<String, Object> params,
            Integer timeout) {
        try {
            RequestBuilder requestBuilder = RequestBuilder.get()
                    .setUri(url.toURI());
            if(null != params) {
                for(String s : params.keySet()) {
                    if(null != params.get(s)) {
                        requestBuilder.addParameter(s,
                                params.get(s).toString());
                    }
                }
            }
            requestBuilder.setCharset(Charset.forName(this.charset));
            HttpUriRequest request = requestBuilder.build();
            HttpContext context = HttpClientContext.create();
            HttpRequestFutureTask<JSONObject> task = futureRequestExecutionService
                    .execute(request, context, new JSONResponseHandler());
            JSONObject result;
            if(null != timeout && 0 < timeout) {
                result = task.get(timeout, TimeUnit.MILLISECONDS);
            } else {
                result = task.get(this.timeout, TimeUnit.MILLISECONDS);
            }
            return result;
        } catch(URISyntaxException e) {
            LOG.warn("无效的请求URL", e);
        } catch(InterruptedException e) {
            LOG.warn("请求线程被中断", e);
        } catch(ExecutionException e) {
            LOG.warn("线程执行时异常.", e.getCause());
        } catch(TimeoutException e) {
            LOG.warn("请求超时", e);
        }
        return null;
    }

    @Override
    public String getAsString(URL url, Map<String, Object> params,
            Integer timeout) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JSONObject postAsJSON(URL url, Map<String, Object> params,
            Integer timeout) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String postAsString(URL url, Map<String, Object> params,
            Integer timeout) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void shutdown() {
        if(null != futureRequestExecutionService) {
            try {
                futureRequestExecutionService.close();
            } catch(IOException e) {
                LOG.warn("关闭http池错误.", e);
            }
        }
    }

}
