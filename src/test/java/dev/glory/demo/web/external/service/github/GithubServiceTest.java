package dev.glory.demo.web.external.service.github;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import dev.glory.demo.common.http.HttpProperties;
import dev.glory.demo.common.http.okhttp.OkHttpClientBuilder;
import dev.glory.demo.common.http.retrofit.RetrofitServiceGenerator;
import dev.glory.demo.system.properties.external.GithubProperties;
import dev.glory.demo.web.external.service.github.dto.GithubUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GithubServiceTest {

    private static GithubService githubService;

    @BeforeAll
    static void beforeAll() {
        GithubProperties githubProperties = new GithubProperties("https://api.github.com", "/users");
        githubService = new GithubService(createServiceGenerator(), githubProperties);
        githubService.init();
    }

    @Test
    void find_users() throws Exception {
        // given

        // when
        List<GithubUser> users = githubService.findUsers();

        // then
        System.out.println("==> users = " + users);
        assertThat(users).isNotNull().hasSizeGreaterThan(1);
    }

    @Test
    void find_user() throws Exception {
        // given
        String loginId = "gykk16";

        // when
        GithubUser githubUser = githubService.findUserByLoginId(loginId);

        // then
        System.out.println("==> githubUser = " + githubUser);
        assertThat(githubUser.getLogin()).isEqualTo(loginId);
    }

    private static RetrofitServiceGenerator createServiceGenerator() {
        return new RetrofitServiceGenerator(createOkHttpClientBuilder());
    }

    private static OkHttpClientBuilder createOkHttpClientBuilder() {
        return new OkHttpClientBuilder(new HttpProperties(1, 30, 5, 5, 5, 5));
    }
}