package org.hope6537.note.design.mix.chain_command;

/**
 * 具体的LS命令
 */
public class LSCommand extends AbstractCommand {

    @Override
    public String execute(CommandVo vo) {
        CommandName firstNode = super.buildChain(AbstractLS.class).get(0);
        return firstNode.handleMessage(vo);
    }
}
