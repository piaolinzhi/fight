package com.fighthard.httpcomponent.httpclient.component.http.responsehandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.Args;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.utils.ResponseUtils;

public final class JSONResponseHandler
        implements ResponseHandler<JSONObject> {

    private static final Logger LOG = LoggerFactory
            .getLogger(JSONResponseHandler.class);

    private String charset;

    @Override
    public JSONObject handleResponse(HttpResponse response)
            throws ClientProtocolException, IOException, JSONException {
        Args.notNull(response, "http response");
        HttpEntity responseEntity = response.getEntity();
        StatusLine statusLine = response.getStatusLine();
        Integer statusCode = statusLine.getStatusCode();
        LOG.debug("返回状态：" + statusLine.toString());
        String charset = "UTF-8";
        if(StringUtils.isNotBlank(this.charset)) {
            charset = this.charset;
        }
        if(null != responseEntity && 200 == statusCode) {
            byte[] rawBody;
            InputStream responseInputStream = responseEntity.getContent();
            if(ResponseUtils
                    .isGzipped(responseEntity.getContentEncoding())) {
                responseInputStream = new GZIPInputStream(
                        responseEntity.getContent());
            }
            rawBody = ResponseUtils.getBytes(responseInputStream);
            String jsonString = new String(rawBody, charset).trim();
            return new JSONObject(jsonString);
        } else if(301 == statusCode || 302 == statusCode) {
            LOG.debug("请求被重定向");
        }
        return null;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

}
