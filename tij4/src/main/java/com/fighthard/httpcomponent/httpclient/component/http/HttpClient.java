package com.fighthard.httpcomponent.httpclient.component.http;

import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

/**
 * http 请求调用接口.
 * @author plz
 */
public interface HttpClient {

    /**
     * 使用 get 方法发送请求，返回JSON格式的数据.
     * @param url
     *            请求地址.
     * @param params
     *            请求的参数.
     * @param timeout
     *            超时时间.
     * @return JSON格式的请求结果.
     */
    JSONObject getAsJSON(URL url, Map<String, Object> params,
            Integer timeout);

    /**
     * 使用 get 方法发送请求，返回JSON格式的数据.
     * @param url
     *            请求地址.
     * @param params
     *            请求的参数.
     * @param timeout
     *            超时时间.
     * @return 字符串格式的请求结果.
     */
    String getAsString(URL url, Map<String, Object> params,
            Integer timeout);

    /**
     * 使用 post 方法发送请求，返回JSON格式的数据.
     * @param url
     *            请求地址.
     * @param params
     *            请求的参数.
     * @param timeout
     *            超时时间.
     * @return JSON格式的请求结果.
     */
    JSONObject postAsJSON(URL url, Map<String, Object> params,
            Integer timeout);

    /**
     * 使用 post 方法发送请求，返回JSON格式的数据.
     * @param url
     *            请求地址.
     * @param params
     *            请求的参数.
     * @param timeout
     *            超时时间.
     * @return 字符串格式的请求结果.
     */
    String postAsString(URL url, Map<String, Object> params,
            Integer timeout);

    /**
     * 关闭 http 管理器，防止线程泄漏.
     */
    void shutdown();

}
