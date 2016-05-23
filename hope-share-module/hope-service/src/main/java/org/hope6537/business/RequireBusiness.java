package org.hope6537.business;

import org.hope6537.dto.RequireDto;
import org.hope6537.entity.ResultSupport;

import java.util.List;

/**
 * Created by hope6537 on 16/5/23.
 */
public interface RequireBusiness {

    ResultSupport<List<RequireDto>> getRequiredListByTeacherId(Integer teacherId);

    ResultSupport<List<RequireDto>> getRequireRichListByQuery(RequireDto query);

}
