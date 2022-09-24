package com.nalstudio.basic.component.push;

import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@RequiredArgsConstructor
@Component(value = "pushManager")
public class PushManager {

    private static PushManager instance;

    public static PushManager getInstance() {
        if(instance == null) {
            instance = new PushManager();
        }
        return instance;
    }

    private String fcmPushUrl = "https://fcm.googleapis.com/fcm/send";
    private String fcmPushKey = "";

    private String apnsPassword = "";

    public PushDto.FcmResponseInfo sendFcmPush(String pushToken, PushDto.FCMInfo FCMInfo)
            throws ParseException, IOException, InterruptedException, ExecutionException {

        List<String> pushTokens = new ArrayList<>();
        pushTokens.add(pushToken);
        return sendMultipleFcmPush(pushTokens, FCMInfo);
    }

    public PushDto.FcmResponseInfo sendMultipleFcmPush(List<String> pushTokens, PushDto.FCMInfo FCMInfo)
            throws ParseException, IOException, InterruptedException, ExecutionException {

        String FCM_PUSH_URL = fcmPushUrl;
        String FCM_PUSH_KEY = fcmPushKey;

        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        String responseStr = null;
        String[] ids = pushTokens.stream().toArray(String[]::new);

        client.start();

        final HttpPost request = new HttpPost(FCM_PUSH_URL);
        request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        request.addHeader(HttpHeaders.AUTHORIZATION, "key=" + FCM_PUSH_KEY);

        LinkedHashMap<String, Object> pushEntity = new LinkedHashMap<String, Object>();
        pushEntity.put("registration_ids", ids);
        pushEntity.put("content_available", true);
        pushEntity.put("priority", "high");
        pushEntity.put("apns-priority", "5");
        pushEntity.put("apns-push-type", "background");
        pushEntity.put("data", FCMInfo);
        pushEntity.put("notification", FCMInfo);

        final String pushEntityStr = new GsonBuilder().serializeNulls().create().toJson(pushEntity);


        StringEntity requestEntity = new StringEntity(pushEntityStr, Consts.UTF_8);

        request.setEntity(requestEntity);

        Future<HttpResponse> future = client.execute(request, new FutureCallback<HttpResponse>() {
            @Override
            public void failed(Exception ex) {

            }

            @Override
            public void completed(HttpResponse result) {

            }

            @Override
            public void cancelled() {

            }
        });

        BasicHttpResponse httpResponse = (BasicHttpResponse) future.get();
        responseStr = EntityUtils.toString(httpResponse.getEntity());

        PushDto.FcmResponseInfo info = null;

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            info = new GsonBuilder().serializeNulls().create().fromJson(responseStr, PushDto.FcmResponseInfo.class);
        }

        client.close();
        System.out.println(info);
        return info;
    }
}
