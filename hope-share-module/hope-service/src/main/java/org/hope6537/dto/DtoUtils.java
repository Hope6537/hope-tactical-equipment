package org.hope6537.dto;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class DtoUtils {

    public static <T extends BasicDto> Map<Integer, T> indexObject(List<T> module) {
        Map<Integer, T> result = Maps.newConcurrentMap();
        module.forEach(t -> result.put(t.getId(), t));
        return result;
    }

}
