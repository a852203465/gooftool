package cn.darkjrong.core.exceptions;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 异常，需要自行捕获异常
 *
 * @author Rong.Jia
 * @date 2023/06/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoofException extends Exception implements Serializable {

    private static final long serialVersionUID = -1501020198729282518L;

    public GoofException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public GoofException(String message) {
        super(message);
    }

    public GoofException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public GoofException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public GoofException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    public GoofException(Throwable throwable, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), throwable);
    }


}
