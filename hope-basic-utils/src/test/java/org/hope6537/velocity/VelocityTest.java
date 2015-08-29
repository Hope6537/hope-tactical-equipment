package org.hope6537.velocity;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

/**
 * Created by Hope6537 on 2015/8/29.
 */
public class VelocityTest {

    @Test
    public void testRender() throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("Threshold", "DEBUG");
        map.put("FILELOGER_File", "/log/fileLog.log");
        map.put("THREADLOGE_File", "/log/threadLog.log");
        Reader reader = new FileReader(VelocityHelper.class.getResource("/log4j.properties.vm").getFile());
        Writer writer = VelocityHelper.getInstance().evaluateToWriter(map, reader);
        System.out.println(writer.toString());
    }

}
