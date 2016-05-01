package org.hope6537.string.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.hope6537.string.KMPSearch;
import org.junit.Test;

/**
 * Created by Hope6537 on 2015/3/5.
 */
public class StringTests {

    @Test
    public void testKMP() {
        String pat = "People's Republic of China";
        String[] txtArray = new String[]{"'s"};
        KMPSearch kmpSearch = new KMPSearch(pat);
        for (String txt : txtArray) {
            int offset = kmpSearch.search(txt);
            System.out.println(offset);
        }
    }

    @Test
    public void testJson() {
        String json = "";
        JSONArray jsonObject = JSON.parseArray("");
    }

}
