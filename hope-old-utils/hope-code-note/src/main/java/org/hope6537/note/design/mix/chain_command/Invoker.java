package org.hope6537.note.design.mix.chain_command;

/**
 * 命令分发
 */
public class Invoker {

    public String exec(String commandStr) {
        //返回值
        String result = "";
        //解析命令对象
        CommandVo vo = new CommandVo(commandStr);
        //检查是否支持
        if (CommandEnum.getNames().contains(vo.getCommandName())) {
            //产生命令对象
            String className = CommandEnum.valueOf(vo.getCommandName()).getValue();
            AbstractCommand command;
            try {
                command = (AbstractCommand) Class.forName(className).newInstance();
                result = command.execute(vo);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            result = "undefined command";
        }
        return result;
    }
}
