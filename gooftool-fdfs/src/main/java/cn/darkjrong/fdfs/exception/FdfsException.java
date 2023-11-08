package cn.darkjrong.fdfs.exception;

import cn.darkjrong.core.exceptions.GoofRuntimeException;

import java.io.Serializable;

/**
 *  FastDFS 异常类
 *
 * @author Rong.Jia
 * @date 2021/07/25 09:58:21
 */
public class FdfsException extends GoofRuntimeException implements Serializable {

    public FdfsException(Throwable e) {
        super(e);
    }

    public FdfsException(String message) {
        super(message);
    }

    public FdfsException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public FdfsException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public FdfsException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    public FdfsException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }
}
