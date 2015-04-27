package org.hope6537.note.design.mix.chain_command;

/**
 * 显示隐藏文件的LS命令
 */
public class LS_A extends AbstractLS {

    @Override
    protected String getOperateParam(CommandVo vo) {
        return FileManager.ls_a(vo.formatData());
    }

    @Override
    protected String echo(CommandVo vo) {
        return super.A_PARAM;
    }
}
