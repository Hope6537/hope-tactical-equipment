package org.hope6537.note.design.mix.chain_command;

/**
 * 抽象命令名类
 */
public abstract class CommandName {

    /**
     * 下一个执行命令
     */
    private CommandName nextOperator;

    /**
     * 判断处理
     */
    public final String handleMessage(CommandVo vo) {
        String result;
        //判断是否为自己处理的参数
        if (vo.getParam().size() == 0 || vo.getParam().contains(this.getOperateParam(vo))) {
            //处理任务
            result = this.echo(vo);
        } else {
            if (this.nextOperator != null) {
                result = this.nextOperator.handleMessage(vo);
            } else {
                result = "undefined command";
            }
        }
        return result;
    }

    public CommandName getNextOperator() {
        return nextOperator;
    }

    public void setNextOperator(CommandName nextOperator) {
        this.nextOperator = nextOperator;
    }

    /**
     * 后缀参数 每个处理者都要有
     */
    protected abstract String getOperateParam(CommandVo vo);

    /**
     * 处理任务
     */
    protected abstract String echo(CommandVo vo);
}
