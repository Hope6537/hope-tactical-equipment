package org.hope6537.business.impl;

import org.hope6537.bo.ComicBusiness;
import org.hope6537.dto.ComicDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.service.ComicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by dintama on 16/3/23.
 */
@Service("comicBusiness")
public class ComicBusinessImpl implements ComicBusiness {

    @Resource
    private ComicService comicService;

    @Override
    public ResultSupport<List<ComicDto>> getComicListByName(ComicDto query) {
        ComicDto comicTitle = new ComicDto();
        comicTitle.setTitle(query.getTitle());
        ComicDto comicAuthor = new ComicDto();
        comicAuthor.setAuthor(query.getAuthor());
        ResultSupport<List<ComicDto>> comicListByQuery = comicService.getComicListByQuery(comicTitle);
        if(!comicListByQuery.isSuccess()){
            return comicListByQuery.castToReturnFailed(null);
        }
        ResultSupport<List<ComicDto>> comicListByQuery1 = comicService.getComicListByQuery(comicAuthor);
        if(!comicListByQuery1.isSuccess()){
            return comicListByQuery1.castToReturnFailed(null);
        }
        List<ComicDto> result = comicListByQuery.getModule();
        result.addAll(comicListByQuery1.getModule());
        return ResultSupport.getInstance(true, "[关联查询成功]", result);
    }
}
