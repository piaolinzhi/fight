package com.fighthard.httpcomponent.unirest;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpHost;
import org.json.JSONObject;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

public class UnirestPost {
    public static void main(String[] args) throws UnirestException,
            InterruptedException, ExecutionException, TimeoutException {
        // appid=db94ae3d-49b4-4d1b-8f25-6e05af692078&data=%7B%22ucmed_doctor_id%22%3A%22UD150714093856471486%22%2C%22card_type%22%3A1%2C%22card_id%22%3A%22610928198808082525%22%2C%22name%22%3A%22%E6%9D%8E%E5%A4%A7%E5%9B%9B%22%2C%22sex%22%3A%22M%22%2C%22phone%22%3A%2213388888888%22%2C%22email%22%3A%22xxx%40163.com%22%2C%22ucmed_unit_doctor_id%22%3A%22UH150714114558743331%22%2C%22hospital_org_code%22%3A%22470003222%22%2C%22section_name%22%3A%22%E5%91%BC%E5%90%B8%E5%86%85%E7%A7%91%22%2C%22common_section_no%22%3A%22031201%22%2C%22work_no%22%3A%229999999%22%7D
        JSONObject data = new JSONObject();
        // data.put("card_type", 1);
        // data.put("card_id", "610928198808082525");
        data.put("name", "李大四");
        // data.put("sex", "M");
        // data.put("birthday", "1980-11-07");
        data.put("phone", "13388887777");
        // data.put("email", "abc@163.com");
        // data.put("hospital_org_code", "470003222");
        // data.put("section_name", "呼吸内科");
        // data.put("common_section_no", "031201");
        // data.put("work_no", "9999999");

        /*
         * HttpResponse<JsonNode> strResponse = Unirest
         * .post("http://90.ylxz.zwjk.com/Doctor/VerifyDoctorInfo")
         * .header("accept", "application/text") .queryString("appid",
         * "db94ae3d-49b4-4d1b-8f25-6e05af692078") .queryString("data",
         * data.toString()).asJson(); System.out.println(strResponse.getBody());
         */
        Unirest.setProxy(new HttpHost("127.0.0.1", 8888));
       
            Future<HttpResponse<JsonNode>> future = Unirest
                    .post("http://90.ylxz.zwjk.com/Doctor/VerifyDoctorInfo")
                    .header("accept", "application/json")
                    /*.queryString("appid",
                            "db94ae3d-49b4-4d1b-8f25-6e05af692078")*/
                    .queryString("data", data.toString())
                    .asJsonAsync(new Callback<JsonNode>() {

                        public void failed(UnirestException e) {
                            System.out.println("The request has failed");
                        }

                        public void completed(
                                HttpResponse<JsonNode> response) {
                            try {
                                Thread.sleep(1);
                            } catch(InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            int code = response.getStatus();
                            Headers headers = response.getHeaders();
                            JsonNode body = response.getBody();
                            InputStream rawBody = response.getRawBody();

                        }

                        public void cancelled() {
                            System.out.println(
                                    "The request has been cancelled");
                        }

                    });
            // HttpResponse<JsonNode> asyncResponse =
            // future.get(1,TimeUnit.SECONDS);

//            System.out.println(i);
            HttpResponse<JsonNode> asyncResponse = future.get(100,TimeUnit.SECONDS);
            System.out.println(asyncResponse.getStatusText());
            System.out.println(asyncResponse.getBody());
            System.out.println(asyncResponse.toString());
        
    }
}
