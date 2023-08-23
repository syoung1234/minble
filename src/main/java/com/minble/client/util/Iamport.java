package com.minble.client.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Iamport {

    public Object subscriber(String imp_key, String imp_secret, String customer_uid, String merchant_uid) {

        /******************* 토큰 발급 *******************/
        // 인증 토큰 발급 받기
        Map<String, String> req = new HashMap<>();
        req.put("imp_key", imp_key);
        req.put("imp_secret", imp_secret);

        String url = "https://api.iamport.kr/users/getToken";

        RequestEntity<Map<String, String>> requestEntity = RequestEntity
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(req);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> response = restTemplate.exchange(requestEntity, JSONObject.class);

        System.out.println(response);
        System.out.println(response.getBody());

        JSONParser jsonParser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();

        String accessToken = null;
        // object -> json 변환 
        try {
            String jsonStr = mapper.writeValueAsString(response.getBody().get("response"));
            System.out.println(jsonStr);

            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonStr);
            System.out.println(jsonObject.get("access_token"));
            accessToken = jsonObject.get("access_token").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        /******************* 결제 요청 *******************/
        // header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", accessToken);

        // body
        Map<String, String> payReq = new HashMap<>();
        payReq.put("customer_uid", customer_uid);
        payReq.put("merchant_uid", merchant_uid);
        payReq.put("amount", "3300");
        payReq.put("name", "Message 구독");

        String payUrl = "https://api.iamport.kr/subscribe/payments/again";

        RequestEntity<Map<String, String>> payRequestEntity = RequestEntity
                .post(payUrl)
                .headers(headers)
                .body(payReq);

        RestTemplate payRestTemplate = new RestTemplate();
        ResponseEntity<JSONObject> payResponse = payRestTemplate.exchange(payRequestEntity, JSONObject.class);

        return payResponse.getBody();
    
    }
    
}
