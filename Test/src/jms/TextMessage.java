package jms;

/**
 * Created by qingyang on 2018/1/25.
 */
public class TextMessage implements Message {
    @Override
    public void send() {
        System.out.println("send");
    }
}
