package com.clayons.interviewquestions.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jaychung on 5/31/16.
 */
public class Post {
    @SerializedName("userId")
    Long userId;
    @SerializedName("id")
    Long id;
    @SerializedName("title")
    String title;
    @SerializedName("body")
    String body;
}
