package org.hope6537.hadoop.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Hope6537 on 2015/1/28.
 */
public class RPCClient {

    public static void main(String[] args) {
        try {
            PublicInterface proxy = RPC.getProxy(
                    PublicInterface.class,
                    100,
                    new InetSocketAddress("127.0.0.1", 6537),
                    new Configuration());
            String res = proxy.getMeg("C:/");
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
