package dev.glory.demo.web.external.service.github;

import java.util.List;

import dev.glory.demo.web.external.service.github.dto.GithubUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface RetGithubService {

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<List<GithubUser>> users(
            @Url String url);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GithubUser> user(
            @Url String url
    );

}
