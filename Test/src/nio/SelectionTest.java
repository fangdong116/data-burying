package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by qingyang on 2018/1/19.
 */
public class SelectionTest {
    void select() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        Selector selector = Selector.open();

        channel.configureBlocking(false);

        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);


        while(true) {

            int readyChannels = selector.select();

            if(readyChannels == 0) continue;


            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while(keyIterator.hasNext()) {

                SelectionKey skey = keyIterator.next();

                if(skey.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.

                } else if (skey.isConnectable()) {
                    // a connection was established with a remote server.
                    SocketChannel sc = (SocketChannel) skey.channel();
                    sc.configureBlocking(false);
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    buf.flip();
                    int pos = sc.read(buf);
                    while(pos != -1){
                        buf.getChar();
                    }

                } else if (skey.isReadable()) {
                    // a channel is ready for reading

                } else if (skey.isWritable()) {
                    // a channel is ready for writing
                }

                keyIterator.remove();
            }
        }
    }
}

