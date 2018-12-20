package jms;

/**
 * Created by qingyang on 2018/1/25.
 */
public class TcpConnection implements Connection, Message{
    @Override
    public void connect() {
        System.out.println("connect tcp!");
    }

    @Override
    public void send() {
        System.out.println("send message");
    }
}
