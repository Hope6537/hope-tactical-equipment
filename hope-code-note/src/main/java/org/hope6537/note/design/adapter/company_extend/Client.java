package org.hope6537.note.design.adapter.company_extend;

import org.hope6537.note.design.adapter.company.IUserinfo;
import org.junit.Test;

/**
 * Created by Hope6537 on 2015/4/10.
 */
public class Client {

    @Test
    public void test() {
        IUserinfo userinfo = OuterUserinfo.UserinfoFactory.getInstance();
        System.out.println(userinfo.getHomeAddress());
    }

}
