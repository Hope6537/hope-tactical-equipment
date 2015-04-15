package org.hope6537.note.design.adapter.company_extend;

import java.util.HashMap;
import java.util.Map;


public interface IOutInterfaces {

}

/**
 * 基本信息接口
 */
interface IOuterUserBaseInfo {

    public Map<String, String> getUserBaseInfo();

}

/**
 * 家庭信息接口
 */
interface IOuterUserHomeInfo {

    public Map<String, String> getUserHomeInfo();

}

/**
 * 工作信息接口
 */
interface IOuterUserOfficeInfo {

    public Map<String, String> getUserOfficeInfo();

}

class OuterUserBaseInfo implements IOuterUserBaseInfo {

    @Override
    public Map<String, String> getUserBaseInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("username", "name");
        result.put("number", "18686602599");
        return result;
    }
}

class OuterUserHomeInfo implements IOuterUserHomeInfo {

    @Override
    public Map<String, String> getUserHomeInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("Hnumber", "0431-55555555");
        result.put("address", "alibaba");
        return result;
    }
}

class OuterUserOfficeInfo implements IOuterUserOfficeInfo {
    @Override
    public Map<String, String> getUserOfficeInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("job", "Java Developer");
        result.put("Onumber", "0571-45655656");
        return result;
    }
}