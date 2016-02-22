package com.fighthard.httpcomponent.httpclient;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;

public class MessageHeaders {

    public MessageHeaders() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
                HttpStatus.SC_OK, "OK");
        response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
        response.addHeader("Set-Cookie",
                "c2=b; path=\"/\", c3=c; domain=\"localhost\"");
        // Get header info.
        Header h1 = response.getFirstHeader("Set-Cookie");
        System.out.println(h1);
        Header h2 = response.getLastHeader("Set-Cookie");
        System.out.println(h2);
        Header[] hs = response.getHeaders("Set-Cookie");
        System.out.println(hs.length);
        // Header iterator.
        HeaderIterator it = response.headerIterator("Set-Cookie");

        while(it.hasNext()) {
            System.out.println(it.next());
        }

        // parse header content using iterator
        System.out.println(
                "<<<<<<<<<parse header content using iterator>>>>>>>>>");
        HeaderElementIterator it1 = new BasicHeaderElementIterator(
                response.headerIterator("Set-Cookie"));
        while(it1.hasNext()) {
            HeaderElement elem = it1.nextElement();
            System.out.println(elem.getName() + " = " + elem.getValue());
            NameValuePair[] params = elem.getParameters();
            for(int i = 0; i < params.length; i++) {
                System.out.println(" " + params[i]);
            }
        }
    }

}
