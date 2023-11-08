package cn.darkjrong.hdfs.exception;

import cn.darkjrong.core.exceptions.GoofRuntimeException;

import java.io.Serializable;

/**
 * hdfs异常
 *
 * @author Rong.Jia
 * @date 2022/01/02
 */
public class HdfsException extends GoofRuntimeException implements Serializable {

    private static final long serialVersionUID = -7855884152357779203L;

    public HdfsException(Throwable e) {
        super(e);
    }

    public HdfsException(String message) {
        super(message);
    }

    public HdfsException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public HdfsException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public HdfsException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    public HdfsException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }
}
