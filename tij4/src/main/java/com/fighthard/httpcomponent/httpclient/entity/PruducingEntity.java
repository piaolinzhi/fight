package com.fighthard.httpcomponent.httpclient.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.message.BasicNameValuePair;

public class PruducingEntity {
    public static void main(String[] args) {

        // file entity
        File file = new File("somefile.txt");
        FileEntity fileEntity = new FileEntity(file,
                ContentType.create("text/plain", "UTF-8"));
        HttpPost httppost = new HttpPost("http://localhost/action.do");
        httppost.setEntity(fileEntity);

        // form entity
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("param1", "value1"));
        formparams.add(new BasicNameValuePair("param2", "value2"));
        
        // use the so called URL encoding to encode parameters.
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                formparams, Consts.UTF_8);
        HttpPost postForm = new HttpPost("http://localhost/handler.do");
        postForm.setEntity(formEntity);

    }

}
