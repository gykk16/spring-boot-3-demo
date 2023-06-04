package dev.glory.demo.system.annotation.logrequest;

import lombok.Getter;

@Getter
public enum RequestAction {

    //
    REGISTER("가입"),
    REFRESH_TOKEN("토큰 재발급"),

    ;


    private final String description;

    RequestAction(String description) {
        this.description = description;
    }
}
