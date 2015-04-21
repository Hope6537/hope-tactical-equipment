package org.hope6537.note.design.state.example;

/**
 * 抽象状态角色
 */
public abstract class AbstractState {

    protected Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 行为1
     */
    public abstract void handle1();

    /**
     * 行为2
     */
    public abstract void handle2();
}
