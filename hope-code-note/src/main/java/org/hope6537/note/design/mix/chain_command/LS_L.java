package org.hope6537.note.design.mix.chain_command;

/**
 * 详情LS命令
 */
public class LS_L extends AbstractLS {
    @Override
    protected String getOperateParam(CommandVo vo) {
        return FileManager.ls_l(vo.formatData());
    }

    @Override
    protected String echo(CommandVo vo) {
        return super.L_PARAM;
    }
}
