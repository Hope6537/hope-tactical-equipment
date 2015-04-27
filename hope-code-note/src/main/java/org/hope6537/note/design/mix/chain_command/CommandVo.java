package org.hope6537.note.design.mix.chain_command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 命令对象
 */
public class CommandVo {

    /**
     * 分隔符
     */
    public final static String DIVIDE_FLAG = " ";

    /**
     * 参数前的符号
     */
    public static final String PREFIX = "-";

    /**
     * 命令名成
     */
    private String commandName = "";

    /**
     * 参数列表
     */
    private List<String> paramList = new ArrayList<>();

    /**
     * 操作数列表
     */
    private List<String> dataList = new ArrayList<>();

    public CommandVo(String commandStr) {
        //常规判断
        if (commandStr != null && commandStr.length() != 0) {
            //分隔符拆分出执行符号
            String[] complexStr = commandStr.split(CommandVo.DIVIDE_FLAG);
            this.commandName = complexStr[0];
            //将参数放入List中
            for (int i = 1; i < complexStr.length; i++) {
                String str = complexStr[i];
                //包含前缀符号-则认为是参数
                if (str.indexOf(CommandVo.PREFIX) == 0) {
                    this.paramList.add(str.replace(CommandVo.PREFIX, "").trim());
                } else {
                    this.dataList.add(str.trim());
                }
            }

        } else {
            System.out.println("undefined command");
        }
    }

    /**
     * 命令名
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * 获得参数
     */
    public List<String> getParam() {
        if (this.paramList.size() == 0) {
            this.paramList.add("");
        }
        return new ArrayList<>(new HashSet<>(this.paramList));
    }

    /**
     * 获得操作数
     */
    public List<String> getData() {
        return dataList;
    }

    public String formatData() {
        return "command";
    }
}
