package org.hope6537.business;

import org.hope6537.dto.EventDto;
import org.hope6537.dto.StudentDto;
import org.hope6537.entity.ResultSupport;

import java.util.List;

/**
 * Created by hope6537 on 16/5/21.
 */
public interface EventBusiness {

    ResultSupport<List<EventDto>> getEventRichListByQuery(EventDto query);

    ResultSupport<List<StudentDto>> getEventRichListByParentIdGroupByStudentId(Integer parentId);
}
