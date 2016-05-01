package org.hope6537.note.design.chain.example;

/**
 * 定义一个请求
 */
public class Request {

    private Level level;

    public Request(Level level) {
        this.level = level;
    }

    public Request(Integer level) {
        this.level = new Level(level);
    }

    public Level getRequestLevel() {
        return level;
    }
}

