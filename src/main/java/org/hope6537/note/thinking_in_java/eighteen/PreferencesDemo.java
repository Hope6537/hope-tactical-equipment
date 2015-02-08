package org.hope6537.note.thinking_in_java.eighteen;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 一个持久化的键值对测验
 * @signdate 2014年7月25日下午5:08:19
 * @company Changchun University&SHXT
 */
public class PreferencesDemo {

    public static void main(String[] args) throws IOException, BackingStoreException {
        Preferences prefs = Preferences
                .userNodeForPackage(PreferencesDemo.class);
        prefs.put("Location", "ChangChun");
        prefs.putInt("Age", 27);
        int usageCount = prefs.getInt("UsageCount", 0);
        usageCount++;
        prefs.putInt("UsageCount", usageCount);
        for (String key : prefs.keys()) {
            System.out.println(key + " : " + prefs.get(key, null));
        }
        System.out.println("Where ? " + prefs.get("Location", null));
    }

}
