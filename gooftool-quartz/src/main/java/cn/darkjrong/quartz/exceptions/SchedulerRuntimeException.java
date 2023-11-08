package cn.darkjrong.quartz.exceptions;

import cn.darkjrong.core.exceptions.GoofRuntimeException;

import java.io.Serializable;

/**
 * 调度器运行时异常
 *
 * @author Rong.Jia
 * @date 2021/10/21
 */
public class SchedulerRuntimeException extends GoofRuntimeException implements Serializable {

    private static final long serialVersionUID = -238316954128154128L;

    public SchedulerRuntimeException(Throwable e) {
        super(e);
    }

    public SchedulerRuntimeException(String message) {
        super(message);
    }

    public SchedulerRuntimeException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public SchedulerRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public SchedulerRuntimeException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    public SchedulerRuntimeException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }
}
