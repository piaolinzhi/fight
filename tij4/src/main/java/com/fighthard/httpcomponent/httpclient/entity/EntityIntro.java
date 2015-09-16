package com.fighthard.httpcomponent.httpclient.entity;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class EntityIntro {

    public static void main(String[] args)
            throws ParseException, IOException {
        StringEntity myEntity = new StringEntity("important message",
                ContentType.create("text/plain", "UTF-8"));

        System.out.println(myEntity.getContentType());
        System.out.println(myEntity.getContentLength());
        System.out.println(EntityUtils.toString(myEntity));
        System.out.println(EntityUtils.toByteArray(myEntity).length);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost/");
        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            HttpEntity entity = response.getEntity();
            if(entity != null) {
                long len = entity.getContentLength();
                // entity consume method 1
                if(len != -1 && len < 2048) {
                    System.out.println(EntityUtils.toString(entity));
                } else {
                    // Stream content out
                }
                // entity consume method 2.
                InputStream instream = entity.getContent();
                try {
                    // do something useful
                } finally {
                    instream.close();
                }
            }
        } finally {
            // Release of low level resources.
            response.close();
        }
    }

}
