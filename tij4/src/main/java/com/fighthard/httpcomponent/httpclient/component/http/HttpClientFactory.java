package com.fighthard.httpcomponent.httpclient.component.http;

/**
 * HttpClient 工厂类.
 * @author plz
 *
 */
public interface HttpClientFactory {
    /**
     * 获取HttpClient辅助类实例.
     * @return
     */
    HttpClient getInstance();

}
