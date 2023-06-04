package dev.glory.demo.system.aop.logtrace;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TreadLocalLogTrace implements LogTrace {

    private static final String START_PREFIX = "--> ";
    private static final String END_PREFIX   = "<-- ";
    private static final String EX_PREFIX    = "<X- ";

    private final ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();


    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder.get();
        long startTimeMs = System.currentTimeMillis();

        log.info("# {}{}", addSpace(START_PREFIX, traceId.getLevel()), message);

        return new TraceStatus(traceId, startTimeMs, message);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        long endTimeMs = System.currentTimeMillis();
        long elapsedTimeMs = endTimeMs - status.getStartTimeMs();

        TraceId traceId = status.getTraceId();

        if (e == null) {
            log.info("# {}{} , elapsedTimeMs={}ms",
                    addSpace(END_PREFIX, traceId.getLevel()), status.getMessage(), elapsedTimeMs);
        } else {
            log.warn("# {}{} , elapsedTimeMs={}ms , ex={}",
                    addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), elapsedTimeMs, e.toString());
        }

        releaseTraceId();
    }

    private void syncTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId == null) {
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceId.setNextLevel());
        }
    }

    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId.isFirstLevel()) {
            traceIdHolder.remove();
        } else {
            traceIdHolder.set(traceId.setPrevLevel());
        }
    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
