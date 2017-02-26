package com.clayons.interviewquestions.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.util.Log;

import com.clayons.interviewquestions.Constants;
import com.clayons.interviewquestions.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyIntentService extends IntentService {
    private static final String PREFIX = MyIntentService.class.getSimpleName() + ": ";

    public static final String ACTION_GET_USERS = "com.clayons.interviewquestions.services.action.GET_USERS";
    private static final String URL_SUFFIX_GET_USERS = "/users";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_USERS.equals(action)) {
                getUsers();
            }
        }
    }

    private void getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(Constants.HOST + URL_SUFFIX_GET_USERS)
                    .build();

            Response response = client.newCall(request).execute();
            String res = response.body().string();
            users = new Gson().fromJson(res, new TypeToken<ArrayList<User>>() {}.getType());
        } catch (Exception e) {
            Log.e(PREFIX, e.toString());
        }

        EventBus.getDefault().post(users);
    }
}
