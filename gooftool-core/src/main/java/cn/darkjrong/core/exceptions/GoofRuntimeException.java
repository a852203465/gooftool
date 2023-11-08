package cn.darkjrong.core.exceptions;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 运行时异常
 *
 * @author Rong.Jia
 * @date 2023/06/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoofRuntimeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -1501020198729282518L;

    public GoofRuntimeException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public GoofRuntimeException(String message) {
        super(message);
    }

    public GoofRuntimeException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public GoofRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public GoofRuntimeException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    public GoofRuntimeException(Throwable throwable, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), throwable);
    }


}
