package org.hope6537.note.design.state.example;

/**
 * 具体状态角色
 */
public class State2 extends AbstractState {
    /**
     * 行为交给状态1进行委托
     */
    @Override
    public void handle1() {
        //设置当前状态为State1
        super.context.setCurrentState(Context.STATE_1);
        super.context.handle1();
    }

    @Override
    public void handle2() {
        System.out.println("正在执行任务2" + " 当前状态为" + context.getCurrentState().getClass().getSimpleName());
    }
}
