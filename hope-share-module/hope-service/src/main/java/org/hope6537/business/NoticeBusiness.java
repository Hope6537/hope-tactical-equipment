package org.hope6537.business;

import org.hope6537.dto.NoticeDto;
import org.hope6537.entity.ResultSupport;

import java.util.List;

/**
 * 通知业务
 * Created by hope6537 on 16/5/21.
 */
public interface NoticeBusiness {

    /**
     * 通过Query获得具有Classes信息的Notice列表
     *
     * @param query
     * @return
     */
    ResultSupport<List<NoticeDto>> getNoticeRichListByQuery(NoticeDto query);

    /**
     * 根据家长ID获取所有它能看到的通知
     *
     * @param parentId
     * @return
     */
    ResultSupport<List<NoticeDto>> getNoticeRichListByParentId(Integer parentId);

}
