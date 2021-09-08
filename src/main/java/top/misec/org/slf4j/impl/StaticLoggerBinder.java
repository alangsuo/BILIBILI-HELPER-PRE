package top.misec.org.slf4j.impl;

import org.apache.logging.slf4j.Log4jLoggerFactory;
import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import lombok.Setter;

/**
 * StaticLoggerBinder.
 *
 * @author itning
 * @since 2021/5/2 17:55
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {

    private static final String JUL_LOGGER_FACTORY_CLASS_STR = org.slf4j.impl.JDK14LoggerFactory.class.getName();
    private static final String LOG4J2_LOGGER_FACTORY_CLASS_STR = Log4jLoggerFactory.class.getName();
    @Setter
    private static LogImpl LOG_IMPL = LogImpl.LOG4J2;


    @Override
    public ILoggerFactory getLoggerFactory() {
        switch (LOG_IMPL) {
            case JUL:
                return new org.slf4j.impl.JDK14LoggerFactory();
            case LOG4J2:
            default:
                return new Log4jLoggerFactory();
        }
    }

    @Override
    public String getLoggerFactoryClassStr() {
        switch (LOG_IMPL) {
            case JUL:
                return JUL_LOGGER_FACTORY_CLASS_STR;
            case LOG4J2:
            default:
                return LOG4J2_LOGGER_FACTORY_CLASS_STR;
        }
    }

    public enum LogImpl {
        /**
         * 日志 .
         */
        JUL, LOG4J2
    }
}
