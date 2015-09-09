package com.fighthard.httpcomponent.httpclient.component;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;

/**
 * <p>
 * Subclass of the Apache {@link DefaultHttpClient} that is configured with
 * reasonable default settings and registered schemes for Android, and also lets
 * the user add {@link HttpRequestInterceptor} classes. Don't create this
 * directly, use the {@link #newInstance} factory method.
 * </p>
 * <p/>
 * <p>
 * This client processes cookies but does not retain them by default. To retain
 * cookies, simply add a cookie store to the HttpContext:
 * 
 * <pre>
 * context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
 * </pre>
 * </p>
 */
public final class CommonHttpClient implements HttpClient {

    /***
     * 远程http请求超时长度设置(unit:millisecond).
     */
    public static final int REMOTE_HTTP_TIMEOUT = 20 * 1000;
    /**
     * 远程SOCKET请求超时长度设置(unit:millisecond).
     */
    public static final int REMOTE_SOCKET_TIMEOUT = 20 * 1000;
    /**
     * http连接池最大的连接请求时间数.
     */
    public static final int HTTP_MAX_TOTAL_CONNECTIONS = 100;

    /**
     * Create a new HttpClient with reasonable defaults (which you can update).
     * @param userAgent
     *            to report in your HTTP requests.
     * @return AndroidHttpClient for you to use for all your requests.
     */

    private static PoolingHttpClientConnectionManager cm;

    static {
        SSLContext sslContext = SSLContexts.createSystemDefault();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, NoopHostnameVerifier.INSTANCE);
        HttpsURLConnection
                .setDefaultHostnameVerifier(NoopHostnameVerifier.INSTANCE);
        RegistryBuilder<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslsf);
        Registry<ConnectionSocketFactory> registry = socketFactoryRegistry.build();
        cm = new PoolingHttpClientConnectionManager(registry);
        cm.setValidateAfterInactivity(-1);
        cm.setMaxTotal(100);
    }

    public static CommonHttpClient newInstance(String userAgent) {
        
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectTimeout(REMOTE_HTTP_TIMEOUT)
                .setSocketTimeout(REMOTE_SOCKET_TIMEOUT)
                .setRedirectsEnabled(false)
                .setAuthenticationEnabled(false);
        if(StringUtils.isNotBlank(userAgent)) {
            // requestConfigBuilder.set
        }
        RequestConfig defaultRequestConfig = requestConfigBuilder.build();
        HttpParams params = new BasicHttpParams();

        ConnectionConfig.Builder conConfigBuilder = ConnectionConfig
                .custom().setBufferSize(4 * 8192);
        ConnectionConfig connectionConfig = conConfigBuilder.build();

        // Don't handle redirects -- return them to the caller. Our code
        // often wants to re-POST after a redirect, which we must do ourselves.
        // HttpClientParams.setRedirecting(params, false);
        // HttpClientParams.setAuthenticating(params, false);

        // Set the specified user agent and register standard protocols.
        if(userAgent != null) {
            HttpProtocolParams.setUserAgent(params, userAgent);
        }

        
        // SchemeRegistry schemeRegistry = new SchemeRegistry();
        // schemeRegistry.register(new Scheme("http",
        // PlainSocketFactory.getSocketFactory(), 80));
        // schemeRegistry.register(new Scheme("https", sslsf, 443));
        // ClientConnectionManager manager = new ThreadSafeClientConnManager(
        // params, schemeRegistry);

        // We use a factory method to modify superclass initialization
        // parameters without the funny call-a-static-method dance.
//        return new CommonHttpClient(cm, params);
        CloseableHttpClient httpClient=HttpClients.createDefault();
    }

    private final DefaultHttpClient delegate;

    private CommonHttpClient(HttpClientConnectionManager hccm,
            HttpParams params) {
        this.delegate = new DelegateHttpClient(ccm, params);
    }

    /**
     * Release resources associated with this client. You must call this, or
     * significant resources (sockets and memory) may be leaked.
     */
    public void close() {
        getConnectionManager().shutdown();
    }

    public HttpParams getParams() {
        return delegate.getParams();
    }

    public ClientConnectionManager getConnectionManager() {
        return delegate.getConnectionManager();
    }

    public HttpResponse execute(HttpUriRequest request)
            throws IOException {
        return delegate.execute(request);
    }

    public HttpResponse execute(HttpUriRequest request,
            HttpContext context) throws IOException {
        return delegate.execute(request, context);
    }

    public HttpResponse execute(HttpHost target, HttpRequest request)
            throws IOException {
        HttpResponse httpResponse = delegate.execute(target, request);
        return httpResponse;
    }

    public HttpResponse execute(HttpHost target, HttpRequest request,
            HttpContext context) throws IOException {
        return delegate.execute(target, request, context);
    }

    public <T> T execute(HttpUriRequest request,
            ResponseHandler<? extends T> responseHandler)
                    throws IOException {
        return delegate.execute(request, responseHandler);
    }

    public <T> T execute(HttpUriRequest request,
            ResponseHandler<? extends T> responseHandler,
            HttpContext context) throws IOException {
        return delegate.execute(request, responseHandler, context);
    }

    public <T> T execute(HttpHost target, HttpRequest request,
            ResponseHandler<? extends T> responseHandler)
                    throws IOException {
        return delegate.execute(target, request, responseHandler);
    }

    public <T> T execute(HttpHost target, HttpRequest request,
            ResponseHandler<? extends T> responseHandler,
            HttpContext context) throws IOException {
        return delegate.execute(target, request, responseHandler, context);
    }

    private static class DelegateHttpClient extends DefaultHttpClient {

        private DelegateHttpClient(ClientConnectionManager ccm,
                HttpParams params) {
            super(ccm, params);
        }

        @Override
        protected HttpContext createHttpContext() {
            // Same as DefaultHttpClient.createHttpContext() minus the
            // cookie store.
            HttpContext context = new BasicHttpContext();
            context.setAttribute(ClientContext.AUTHSCHEME_REGISTRY,
                    getAuthSchemes());
            context.setAttribute(ClientContext.COOKIESPEC_REGISTRY,
                    getCookieSpecs());
            context.setAttribute(ClientContext.CREDS_PROVIDER,
                    getCredentialsProvider());
            return context;
        }
    }
}
