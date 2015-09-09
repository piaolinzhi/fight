package com.fighthard.httpcomponent.httpclient;

import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClient {
    public static final String TAG = "ucmed";
    private static final Logger logger = Logger.getLogger(HttpClient.class);

    private String mUrl;
    private String proxyHost;
    private int proxyport;

    public int getProxyport() {
        return proxyport;
    }

    public void setProxyport(int proxyport) {
        this.proxyport = proxyport;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public HttpClient() {
        this(null);
    }

    public HttpClient(String url) {
        mUrl = url;
        setDefaultHostnameVerifier();
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public URL getURL() {
        URL url = null;

        try {
            url = new URL(mUrl);
        } catch(Exception e) {
            logger.error(e);
        }

        return url;
    }
    /**
     * 设置握手过程中主机名较验策略.
     */
    private void setDefaultHostnameVerifier() {
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    public String sendSynchronousRequest(String requestData) {
        logger.debug(TAG + " requestData=" + requestData);
        return sendSynchronousRequest(requestData, null);
    }

    public String sendSynchronousRequest(String requestData,
        ArrayList<BasicHeader> headers) {
        ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("requestData", requestData));
        return sendSynchronousRequest(pairs, headers);
    }
    /**
     * 发送远程请求并得到返回报文.
     * @param requestData 请求
     * @param headers
     * @return
     */
    public HttpResponse sendSynchronousRequestAsHttpResponse(
        String requestData, ArrayList<BasicHeader> headers) {
        ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("requestData", requestData));

        return sendSynchronousRequestAsHttpResponse(pairs, headers);
    }

    public String sendSynchronousRequest(ArrayList<BasicNameValuePair> pairs,
        ArrayList<BasicHeader> headers) {
        String retVal = null;

        try {
            HttpResponse httpResponse = sendSynchronousRequestAsHttpResponse(
                pairs, headers);
            if(null == httpResponse) {
                logger.warn("远程返回数据为null");
                return null;
            }
            HttpEntity entity = httpResponse.getEntity();
            retVal = EntityUtils.toString(entity);
            logger.debug(TAG + " responseData=" + retVal);
        } catch(Exception e) {
            logger.error("远程请求异常：",e);
        }

        return retVal;
    }
    //?为什么设为default的？
    static CommonHttpClient sWapHttpClient = null;
   /**
    * 实际发送请求包的函数.
    */
    public HttpResponse sendSynchronousRequestAsHttpResponse(
        ArrayList<BasicNameValuePair> pairs, ArrayList<BasicHeader> headers) {
        HttpResponse httpResponse = null;

        URL url = getURL();
        UrlEncodedFormEntity entity = null;

        HttpRequest httpRequest = null;
        HttpHost target = null;
        try {
            if(sWapHttpClient == null)
                sWapHttpClient = CommonHttpClient.newInstance("wap"); // TODO

            // configure the proxy.
            HttpParams httpParams = sWapHttpClient.getParams();
            HttpHost proxy = getProxy();
            httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

            String protocol = url.getProtocol();
            int port = url.getPort();
            if(port == -1)
                port = url.getDefaultPort();

            target = new HttpHost(url.getHost(), port, protocol);

            if(pairs != null) {
                httpRequest = new HttpPost(mUrl);
                entity = new UrlEncodedFormEntity(pairs, "utf-8");
                ((HttpPost) httpRequest).setEntity(entity);
            } else {
                httpRequest = new HttpGet(mUrl);
            }
            httpRequest.addHeader("Content-type",
                "application/x-www-form-urlencoded;charset=utf-8");
            httpRequest
                .addHeader(
                    "Accept",
                    "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");

            if(headers != null) {
                for(Header header : headers)
                    httpRequest.addHeader(header);
            }

            // execute the request.
            httpResponse = sWapHttpClient.execute(target, httpRequest);
        } catch(NullPointerException e) {
            logger.error(e);
            try {
                // execute the request again.
                httpResponse = sWapHttpClient.execute(target, httpRequest);
            } catch(Exception e1) {
                logger.error(e);
            }
        } catch(Exception e) {
            logger.error(e);
        }
        return httpResponse;
    }

    public HttpHost getProxy() {
        HttpHost proxy = null;
        if(proxyHost != null)
            proxy = new HttpHost(proxyHost, proxyport, "http");
        return proxy;
    }

}

