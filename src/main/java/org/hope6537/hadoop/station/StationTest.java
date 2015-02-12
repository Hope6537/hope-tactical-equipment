package org.hope6537.hadoop.station;

import org.junit.Test;

/**
 * Created by Hope6537 on 2015/2/12.
 */
public class StationTest {

    @Test
    public void testCreate() throws CastException {
        String line1 = "0000001998\t0047485645\t00000180\t2015-02-12 07:36:09\twww.baidu.com";
        String line2 = "0000000012\t0047483659\t0\t00000020\t2015-02-12 17:53:30";
        StationData stationData1 = new StationData(line1, StationDataType.NET, Flag.timeLine);
        StationData stationData2 = new StationData(line2, StationDataType.POS, Flag.timeLine);
    }

}
