package org.hope6537.note.design.state.example;

/**
 * 具体环境角色
 */
public class Context {

    public static final AbstractState STATE_1 = new State1();
    public static final AbstractState STATE_2 = new State2();

    private AbstractState currentState;

    public AbstractState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(AbstractState currentState) {
        this.currentState = currentState;
        this.currentState.setContext(this);
    }

    /**
     * 行为委托
     */
    public void handle1() {
        this.currentState.handle1();
    }

    public void handle2() {
        this.currentState.handle2();
    }
}
