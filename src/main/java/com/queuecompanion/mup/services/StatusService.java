package com.queuecompanion.mup.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    private static final Logger logger = LoggerFactory.getLogger(StatusService.class);

    private final long timeStarted;

    public StatusService() {
        timeStarted = System.currentTimeMillis();
        logger.info("Service started at {}", timeStarted);
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public long getUptimeMillis() {
        return System.currentTimeMillis() - timeStarted;
    }
}
