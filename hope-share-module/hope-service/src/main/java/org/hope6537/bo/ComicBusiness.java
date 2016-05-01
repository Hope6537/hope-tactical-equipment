package org.hope6537.bo;

import org.hope6537.dto.ComicDto;
import org.hope6537.entity.ResultSupport;

import java.util.List;

/**
 * Created by dintama on 16/3/23.
 */
public interface ComicBusiness {

    /**
     * 通过漫画名OR作者名获取漫画
     *
     * @param query 漫画名/作者名
     * @result ResultSupport.module = List<ComicDto>
     * */
    ResultSupport<List<ComicDto>> getComicListByName(ComicDto query);
}
