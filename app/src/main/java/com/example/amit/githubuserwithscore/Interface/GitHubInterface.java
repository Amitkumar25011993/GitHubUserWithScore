package com.example.amit.githubuserwithscore.Interface;

import com.example.amit.githubuserwithscore.Model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by amit on 2/14/2018.
 */

public interface GitHubInterface {

    @GET("/search/users")
    Call<User> getNameScore(@Query("q") String name);
}
