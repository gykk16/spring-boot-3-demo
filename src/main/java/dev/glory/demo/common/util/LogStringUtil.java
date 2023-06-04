package dev.glory.demo.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.lang.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogStringUtil {

    public static final  String LOG_LINE      = "# =================================================================================================";
    private static final String SUB_LINE      = "# ==================== ";
    private static final String START         = "# ============================== ";
    private static final String END           = " ==============================";
    private static final int    TITLE_MAX_LEN = 35;

    /**
     * 제목 로그
     *
     * @param value 제목
     */
    public static String logTitle(@NonNull String value) {
        final String text;

        int length = value.length();
        if (length <= TITLE_MAX_LEN) {
            text = value + " ".repeat(TITLE_MAX_LEN - length);
        } else {
            text = value.substring(0, TITLE_MAX_LEN);
        }
        return START + text + END;
    }

    /**
     * 부제목 로그
     *
     * @param value 부제목
     */
    public static String logSubTitle(@NonNull String value) {
        return SUB_LINE + value;
    }

}
