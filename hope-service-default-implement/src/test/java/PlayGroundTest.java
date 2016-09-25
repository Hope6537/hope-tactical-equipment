import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.hope6537.dto.ClassesDto;
import org.hope6537.dto.NoticeDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hope6537 on 16/5/21.
 */
public class PlayGroundTest {

    @Test
    public void test1() {

        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setId(1);
        List<ClassesDto> classesDtoList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            ClassesDto classesDto = new ClassesDto();
            classesDto.setId(1000 + i);
            classesDtoList.add(classesDto);
        }
        Map<String, Object> noticeDtoMap = Maps.newConcurrentMap();
        noticeDtoMap.put("id", noticeDto.getId());
        noticeDtoMap.put("entry", noticeDto);
        noticeDtoMap.put("relationList", classesDtoList);

        ArrayList<Map<String, Object>> object = Lists.newArrayList();
        object.add(noticeDtoMap);
        System.out.println(JSON.toJSONString(object));


    }

}
