package org.hope6537.note.design.adapter.company_extend;

import org.hope6537.note.design.adapter.company.IUserinfo;

import java.util.Map;

/**
 * Created by Hope6537 on 2015/4/10.
 */
public class OuterUserinfo implements IUserinfo {

    private IOuterUserBaseInfo outerUserBaseInfo;
    private IOuterUserHomeInfo outerUserHomeInfo;
    private IOuterUserOfficeInfo outerUserOfficeInfo;

    private Map<String, String> baseMap = null;
    private Map<String, String> homeMap = null;
    private Map<String, String> officeMap = null;

    private OuterUserinfo(IOuterUserBaseInfo outerUserBaseInfo, IOuterUserHomeInfo outerUserHomeInfo, IOuterUserOfficeInfo outerUserOfficeInfo) {
        this.outerUserBaseInfo = outerUserBaseInfo;
        this.outerUserHomeInfo = outerUserHomeInfo;
        this.outerUserOfficeInfo = outerUserOfficeInfo;

        this.baseMap = outerUserBaseInfo.getUserBaseInfo();
        this.homeMap = outerUserHomeInfo.getUserHomeInfo();
        this.officeMap = outerUserOfficeInfo.getUserOfficeInfo();
    }

    @Override
    public String getUsername() {
        return baseMap.get("username");
    }

    @Override
    public String getHomeAddress() {
        return homeMap.get("address");
    }

    @Override
    public String getMobileNumber() {
        return baseMap.get("number");
    }

    @Override
    public String getOfficeTelNumber() {
        return officeMap.get("Onumber");
    }

    @Override
    public String getJobPosition() {
        return officeMap.get("job");
    }

    @Override
    public String getHomeTelNumber() {
        return homeMap.get("Hnumber");
    }

    public static class UserinfoFactory {

        private static final IUserinfo USERINFO = new OuterUserinfo(new OuterUserBaseInfo(), new OuterUserHomeInfo(), new OuterUserOfficeInfo());

        public static IUserinfo getInstance() {
            return USERINFO;
        }


    }
}


