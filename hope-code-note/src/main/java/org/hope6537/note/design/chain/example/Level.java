package org.hope6537.note.design.chain.example;

import java.util.Objects;

/**
 * 定义一个处理等级
 */
public class Level {

    public static final Integer INFO = 0;
    public static final Integer ERROR = 1;

    private final Integer level;

    public Level(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(((Level) obj).getLevel(), this.getLevel());
    }
}
