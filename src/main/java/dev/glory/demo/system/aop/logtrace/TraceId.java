package dev.glory.demo.system.aop.logtrace;

import java.util.UUID;

import dev.glory.demo.common.constant.ApplicationConst;


public class TraceId {

    private static final int    FIRST_LEVEL = 1;
    private final        String id;
    private              int    level;


    public TraceId() {
        id = generateId();
        level = FIRST_LEVEL;
    }

    public TraceId setNextLevel() {
        level = level + 1;
        return this;
    }

    public TraceId setPrevLevel() {
        level = level - 1;
        return this;
    }

    public boolean isFirstLevel() {
        return level == FIRST_LEVEL;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    private String generateId() {
        return ApplicationConst.getMdcTraceKey().orElseGet(() -> UUID.randomUUID().toString());
    }
}
