package cn.darkjrong.ftpserver.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * 关闭连接
 * @author Ron.Jia
 * @date 2019/10/16 23:47:22
 */
@Slf4j
@Component("QUIT")
public class QUITCommand extends AbstractCommand {

    /**
     * Execute command
     */
    @Override
    public void execute(final FtpIoSession session,
                        final FtpServerContext context, final FtpRequest request)
            throws IOException {
        session.resetState();
        //业务处理
        InetAddress testAddress = ((InetSocketAddress) session.getRemoteAddress()).getAddress();
        session.write(LocalizedFtpReply.translate(session, request, context,
                FtpReply.REPLY_221_CLOSING_CONTROL_CONNECTION, "QUIT", null));

        session.close(false).awaitUninterruptibly(10000);
        session.getDataConnection().closeDataConnection();
    }
}
