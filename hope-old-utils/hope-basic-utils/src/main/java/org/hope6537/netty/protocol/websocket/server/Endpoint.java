/*
package org.hope6537.netty.protocol.websocket.server;

public class Endpoint implements WebSocket {

    Outbound outbound;

    @Override
    public void onConnect(Outbound outbound) {
        this.outbound = outbound;
    }

    @Override
    public void onMessage(byte opcode, String data) {
        // 在接收到消息时调用
        // 你通常用到的就是这一方法
    }

    @Override
    public void onFragment(boolean more, byte opcode, byte[] data, int offset, int length) {
        // 在完成一段内容时，onMessage被调用
        // 通常不在这一方法中写入东西
    }

    @Override
    public void onMessage(byte opcode, byte[] data, int offset, int length) {
        onMessage(opcode, new String(data, offset, length));
    }

    @Override
    public void onDisconnect() {
        outbound = null;
    }
}*/
