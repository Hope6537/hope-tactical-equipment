package org.hope6537.rest.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dintama on 2016/4/2.
 */
public class JSONUtil {

    public static <T> List<T> parseJsonArrayToList(String jsonArray, Class<T> clz) {
        JSONArray objects = JSON.parseArray(jsonArray);
        List<T> objectList = new LinkedList<>();
        try {
            for (Object t : objects) {
                objectList.add((T) t);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return objectList;
    }


}
