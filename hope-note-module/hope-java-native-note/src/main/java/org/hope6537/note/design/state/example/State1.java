package org.hope6537.note.design.state.example;

/**
 * 具体状态角色
 */
public class State1 extends AbstractState {

    /**
     * 本状态下需要处理的业务逻辑
     */
    public void handle1() {
        System.out.println("正在执行任务1" + " 当前状态为" + context.getCurrentState().getClass().getSimpleName());
    }

    /**
     * 行为委托
     */
    public void handle2() {
        //设置当前状态为State2
        super.context.setCurrentState(Context.STATE_2);
        super.context.handle2();
    }
}
