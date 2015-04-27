package org.hope6537.note.design.mix.chain_command;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象命令
 */
public abstract class AbstractCommand {

    /**
     * 命令执行方法
     */
    public abstract String execute(CommandVo vo);

    /**
     * 建立链表
     */
    protected final List<? extends CommandName> buildChain(Class<? extends CommandName> abstractClass) {
        //取出所有命令名下的子类
        List<Class> classes = ClassUtils.getSonClass(abstractClass);
        //存放命令的实例并建立链式联系 CommandName内部是链式的
        List<CommandName> commandNameList = new ArrayList<>();
        for (Class c : classes) {
            CommandName commandName = null;
            try {
                //产生实例
                commandName = (CommandName) Class.forName(c.getName()).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            //建立链表 setNext
            if (commandNameList.size() > 0) {
                commandNameList.get(commandNameList.size() - 1).setNextOperator(commandName);
            }
            commandNameList.add(commandName);
        }
        return commandNameList;
    }

}
