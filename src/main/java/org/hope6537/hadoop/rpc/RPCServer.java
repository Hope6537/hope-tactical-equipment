package org.hope6537.hadoop.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;

import java.io.IOException;

/**
 * Created by Hope6537 on 2015/1/28.
 */
public class RPCServer implements PublicInterface {

    @Override
    public String getMeg(String path) {
        return "[INFO] the path is " + path;
    }

    public static void main(String[] args) {
        try {
            Configuration configuration = new Configuration();
            Server server = new RPC.Builder(configuration)
                    .setProtocol(PublicInterface.class)
                    .setInstance(new RPCServer())
                    .setBindAddress("192.168.1.127")
                    .setPort(6537).build();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
