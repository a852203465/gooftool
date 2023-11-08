package cn.darkjrong.aliyun.oss.common.exception;

import cn.darkjrong.core.exceptions.GoofRuntimeException;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 项目自定义异常
 * @author Rong.Jia
 * @date 2019/4/3
 */
@EqualsAndHashCode(callSuper = true)
public class AliyunOSSClientException extends GoofRuntimeException implements Serializable {

    private static final long serialVersionUID = -1501020198729282518L;

    public AliyunOSSClientException(String message){
        super(message);
    }

}
