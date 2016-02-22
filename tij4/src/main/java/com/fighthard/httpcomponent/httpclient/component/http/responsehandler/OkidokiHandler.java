package com.fighthard.httpcomponent.httpclient.component.http.responsehandler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class OkidokiHandler implements ResponseHandler<Boolean> {

    public Boolean handleResponse(final HttpResponse response)
            throws ClientProtocolException, IOException {
        return response.getStatusLine().getStatusCode() == 200;
    }
}
