package org.hope6537.note.thinking_in_java.eighteen;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 编码集
 * @signdate 2014年7月24日下午5:44:10
 * @company Changchun University&SHXT
 */
public class AvailableCharSets {

    public static void main(String[] args) {
        SortedMap<String, Charset> charSets = Charset.availableCharsets();
        Iterator<String> iterator = charSets.keySet().iterator();
        while (iterator.hasNext()) {
            String csName = iterator.next();
            System.out.print(csName);
            Iterator<String> aliases = charSets.get(csName).aliases()
                    .iterator();
            if (aliases.hasNext()) {
                System.out.print(" : ");
            }
            while (aliases.hasNext()) {
                System.out.print(aliases.next());
                if (aliases.hasNext()) {
                    System.out.print(" , ");
                }
            }
            System.out.println();
        }
    }
}
