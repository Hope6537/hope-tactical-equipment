package org.hope6537.bo;

import org.hope6537.dto.TestComicDto;
import org.hope6537.entity.ResultSupport;

import java.util.List;

/**
 * Created by hope6537 on 16/1/30.
 */
public interface TestComicBo {

    ResultSupport<List<TestComicDto>> getComicInfoById(Integer... id);

}
