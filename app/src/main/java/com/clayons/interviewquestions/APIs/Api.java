package com.clayons.interviewquestions.APIs;

import com.clayons.interviewquestions.Model.Post;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface Api {

    @GET("/movie/posts")
    void getPopularMovies(
            Callback<List<Post>> callback);
}
