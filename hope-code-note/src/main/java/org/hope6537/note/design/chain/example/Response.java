package org.hope6537.note.design.chain.example;

/**
 * 处理者返回的数据
 */
public class Response {

    private final String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
