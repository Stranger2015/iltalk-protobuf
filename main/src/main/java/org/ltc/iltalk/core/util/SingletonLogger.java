package org.ltc.iltalk.core.util;

import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class SingletonLogger {

    private static final HashMap<Class<?>, org.slf4j.Logger> loggers = new HashMap<>();

    /**
     * @param c
     * @return
     */
    public static org.slf4j.Logger getLogger(Class<?> c) {
        org.slf4j.Logger log;
        if (!loggers.containsKey(c)) {
            log = LoggerFactory.getLogger(c);
            loggers.put(c, log);
        } else {
            log = loggers.get(c);
        }

        return log;
    }

}
