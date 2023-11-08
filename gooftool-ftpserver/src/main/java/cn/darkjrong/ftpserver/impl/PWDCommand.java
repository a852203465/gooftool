package cn.darkjrong.ftpserver.impl;

import org.apache.ftpserver.command.impl.PWD;
import org.springframework.stereotype.Component;

/**
 * 此命令使当前工作目录的名称在回复中返回
 * @author Ron.Jia
 * @date 2019/10/16 23:47:22
 */
@Component("PWD")
public class PWDCommand extends PWD {
}
