package org.hope6537.note.design.mix.chain_command;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令配置方案（枚举方式）
 */
public enum CommandEnum {

    ls("org.hope6537.note.design.mix.chain_command.LSCommand");

    private String value = "";

    CommandEnum(String value) {
        this.value = value;
    }

    public static List<String> getNames() {
        CommandEnum[] commandEnums = CommandEnum.values();
        List<String> names = new ArrayList<>();
        for (CommandEnum commandEnum : commandEnums) {
            names.add(commandEnum.name());
        }
        return names;
    }

    public String getValue() {
        return value;
    }
}
