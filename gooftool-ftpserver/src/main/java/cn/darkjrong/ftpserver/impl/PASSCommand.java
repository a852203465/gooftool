package cn.darkjrong.ftpserver.impl;

import org.apache.ftpserver.command.impl.PASS;
import org.springframework.stereotype.Component;


/**
 * 参数字段是Telnet字符串，用于指定用户的密码。此命令必须紧跟在USER命令之后
 * @author Ron.Jia
 * @date 2019/10/16 23:47:22
 */
@Component("PASS")
public class PASSCommand extends PASS {

}
