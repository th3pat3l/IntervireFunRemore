package com.clayons.interviewquestions.APIs;

import com.clayons.interviewquestions.Constants;

import retrofit.RestAdapter;

public class RestClient {
    private final Api apis;

    public RestClient() {

        RestAdapter apiRest = new RestAdapter.Builder()
                .setEndpoint(Constants.HOST)
                .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
                .build();

        apis = apiRest.create(Api.class);
    }
}
