package com.queuecompanion.mup.dto.response;

public class StatusResponse {
    private final long uptimeMillis;

    public StatusResponse(long uptimeMillis) {
        this.uptimeMillis = uptimeMillis;
    }

    public long getUptimeMillis() {
        return uptimeMillis;
    }

    @Override
    public String toString() {
        return "StatusResponse{" +
                "uptimeMillis=" + uptimeMillis +
                '}';
    }
}
