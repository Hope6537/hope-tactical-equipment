package org.hope6537.note.design.mix.chain_command;

/**
 * 默认LS命令
 */
public class LS extends AbstractLS {

    @Override
    protected String getOperateParam(CommandVo vo) {
        return FileManager.ls(vo.formatData());
    }

    @Override
    protected String echo(CommandVo vo) {
        return super.DEFALUT_PARAM;
    }
}
