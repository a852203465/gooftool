package cn.darkjrong.ftpclient.exceptions;

import cn.darkjrong.core.exceptions.GoofRuntimeException;

import java.io.Serializable;

/**
 * ftp客户端异常
 *
 * @author Rong.Jia
 * @date 2022/01/06
 */
public class FtpClientException extends GoofRuntimeException implements Serializable {

    private static final long serialVersionUID = 5513402809257135408L;

    public FtpClientException(Throwable e) {
        super(e);
    }

    public FtpClientException(String message) {
        super(message);
    }

    public FtpClientException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public FtpClientException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public FtpClientException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    public FtpClientException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }
}
