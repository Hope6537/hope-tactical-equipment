package org.hope6537.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Hope6537 on 2015/3/11.
 */
public class Driver {

    @Test
    public void testJson() throws IOException {

        InputStream in = DataModel.class.getResourceAsStream("data.txt");
        String data = IOUtils.toString(in);
        JSONArray array = JSON.parseArray(data);
        ArrayList<DataModel> dataModels = new ArrayList<>(array.size());
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            dataModels.add(new DataModel(obj.getString("title"),
                    obj.getString("date"),
                    obj.getString("abs"),
                    obj.getString("applicant"),
                    obj.getString("inventor"),
                    obj.getString("expert_name"),
                    obj.getString("expert_org")
            ));
        }
        StringBuilder sqlBuilder = new StringBuilder().append("insert into ??? (title) values ");
        for (DataModel dataModel : dataModels) {
            sqlBuilder.append("(" + dataModel.getTitle() + ")").append(",");
        }
        String sql = sqlBuilder.toString().substring(0, sqlBuilder.toString().length() - 1);
        System.out.println(sql);

    }
}
