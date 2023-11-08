package cn.darkjrong.core.exceptions;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.io.Serializable;

/**
 * 有状态异常
 *
 * @author Rong.Jia
 * @date 2023/06/27
 */
@Getter
public class GoofStatefulException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 5125726279105828472L;

    /**
     * 异常状态码
     */
    private Integer status;

    public GoofStatefulException() {
    }

    public GoofStatefulException(String msg) {
        super(msg);
    }

    public GoofStatefulException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public GoofStatefulException(Throwable throwable) {
        super(throwable);
    }

    public GoofStatefulException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public GoofStatefulException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    public GoofStatefulException(int status, String msg) {
        super(msg);
        this.status = status;
    }

    public GoofStatefulException(int status, Throwable throwable) {
        super(throwable);
        this.status = status;
    }

    public GoofStatefulException(int status, String msg, Throwable throwable) {
        super(msg, throwable);
        this.status = status;
    }

}
