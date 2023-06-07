package dev.glory.demo.web.external.service.github;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

import dev.glory.demo.common.http.retrofit.RetrofitServiceGenerator;
import dev.glory.demo.common.http.retrofit.helper.RetrofitHelper;
import dev.glory.demo.system.properties.external.GithubProperties;
import dev.glory.demo.web.external.code.ExternalErrorCode;
import dev.glory.demo.web.external.exception.ExternalException;
import dev.glory.demo.web.external.service.github.dto.GithubUser;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final RetrofitServiceGenerator serviceGenerator;
    private final GithubProperties         githubProperties;
    private       RetGithubService         service;


    public List<GithubUser> findUsers() {

        try {
            Call<List<GithubUser>> call = service.users(githubProperties.users());

            RetrofitHelper.logRequest(call);
            Response<List<GithubUser>> execute = call.execute();
            RetrofitHelper.logAndCheckResponse(execute);

            List<GithubUser> body = execute.body();

            return body;

        } catch (SocketTimeoutException e) {
            throw new ExternalException(ExternalErrorCode.TIMEOUT_ERROR, e);

        } catch (HttpException e) {
            throw new ExternalException(ExternalErrorCode.HTTP_STATUS_ERROR, e);

        } catch (IOException e) {
            throw new ExternalException(ExternalErrorCode.EXTERNAL_ERROR, e);

        }
    }

    public GithubUser findUserByLoginId(String loginId) {

        try {
            String path = UriComponentsBuilder.fromPath(githubProperties.users())
                    .pathSegment(loginId)
                    .build()
                    .toString();

            Call<GithubUser> call = service.user(path);

            RetrofitHelper.logRequest(call);
            Response<GithubUser> execute = call.execute();
            RetrofitHelper.logAndCheckResponse(execute);

            GithubUser body = execute.body();

            return body;

        } catch (SocketTimeoutException e) {
            throw new ExternalException(ExternalErrorCode.TIMEOUT_ERROR, e);

        } catch (HttpException e) {
            throw new ExternalException(ExternalErrorCode.HTTP_STATUS_ERROR, e);

        } catch (IOException e) {
            throw new ExternalException(ExternalErrorCode.EXTERNAL_ERROR, e);

        }
    }

    @PostConstruct
    public void init() {
        service = serviceGenerator.createService(githubProperties.hostUrl(), RetGithubService.class);
    }

}
