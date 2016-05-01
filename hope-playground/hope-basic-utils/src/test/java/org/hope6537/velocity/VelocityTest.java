package org.hope6537.velocity;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.*;
import java.net.URL;
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
        map.put("THREADLOGER_File", "/log/threadLog.log");
        Reader reader = new FileReader(VelocityHelper.class.getResource("/log4j.properties.vm").getFile());
        File newSpringConfigFile = createNewSpringConfigFile();
        PrintWriter writer = (PrintWriter) VelocityHelper.getInstance().evaluateToWriter(map, reader, newSpringConfigFile.getAbsolutePath());
        writer.flush();
        writer.close();
        reader.close();

    }

    private File createNewSpringConfigFile() throws IOException {
        String classPath = getClass().getResource("/").getFile();
        String renderFolderDir = classPath + "watcher/";
        String renderFileDir = renderFolderDir + "watcher-user-render" + System.currentTimeMillis() + ".xml";

        File folder = new File(renderFolderDir);
        if (!folder.exists()) {
            boolean mkdir = folder.mkdir();
            if (!mkdir) {
                throw new IOException("create folder failed");
            }
        }
        File configFile = new File(renderFileDir);
        if (!configFile.exists()) {
            boolean create = configFile.createNewFile();
            if (!create) {
                throw new IOException("create spring config file failed");
            }
        }
        return configFile;
    }

}
