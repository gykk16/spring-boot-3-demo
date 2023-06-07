package dev.glory.demo.common.http.retrofit.helper;

import lombok.extern.slf4j.Slf4j;

import dev.glory.demo.common.util.LogStringUtil;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

@Component
@Slf4j
public class RetrofitHelper {

    private RetrofitHelper() {
    }


    /**
     * Retrofit 요청 정보 로깅
     *
     * @param call
     */
    public static void logRequest(Call call) {
        log.info(LogStringUtil.logTitle("Http request start"));
        log.info("# >>> {}", call.request());
        log.info(LogStringUtil.logTitle("Http request end"));
    }

    /**
     * Retrofit 응답 정보 로깅 및 http status code 확인
     * <p>
     * . http 상태 코드가 200 ~ 300 사이가 아니면 HttpException 을 던진다.<br>
     * . http 상태 코드 오류로 처리해야 하면 catch 해서 로직 작성하면 된다.
     *
     * @param response
     * @throws HttpException
     */
    public static void logAndCheckResponse(Response response) throws HttpException {
        log.info(LogStringUtil.logTitle("Http response start"));

        try {
            // 상태 코드 200 ~ 300 사이
            if (response.isSuccessful()) {
                log.info("# <<< {}", response);
                log.info("# <<< {}", response.body());
            }
            // 그 외 상태 코드
            else {
                throw new HttpException(response);
            }

        } catch (HttpException e) {
            log.warn("# <<< {}", e.response());
            log.warn("# <<< code : {}", e.code());
            log.warn("# <<< message : {} ", e.message());
            throw e;

        } finally {
            log.info(LogStringUtil.logTitle("Http response end"));
        }
    }
}
