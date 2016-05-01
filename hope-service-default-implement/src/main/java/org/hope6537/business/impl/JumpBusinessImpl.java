package org.hope6537.business.impl;

import org.hope6537.bo.JumpBusiness;
import org.hope6537.dto.ComicDto;
import org.hope6537.dto.JumpDto;
import org.hope6537.dto.SpecialDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.service.ComicService;
import org.hope6537.service.JumpService;
import org.hope6537.service.SpecialService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dintama on 2016/3/13.
 */

@Service("jumpBusiness")
public class JumpBusinessImpl implements JumpBusiness {

    @Resource(name = "specialService")
    private SpecialService specialService;

    @Resource(name = "comicService")
    private ComicService comicService;

    @Resource(name = "jumpService")
    private JumpService jumpService;

    @Override
    public ResultSupport<JumpDto> getJumpBySpecial(JumpDto query) {
        Integer specialId = query.getSpecialId();
        List<Integer> idList = new LinkedList<>();
        //调用getSpecialById的方法
        ResultSupport<SpecialDto> special = specialService.getSpecialById(specialId);
        if (!special.isSuccess()) {
            return special.castToReturnFailed(JumpDto.class);
        }
        //整理得到一个JumpDto
        SpecialDto module = special.getModule();
        JumpDto jumpDto = new JumpDto();
        jumpDto.setSpecialId(module.getId());
        //通过JumpDto中的specialId我们可以获得若干的JumpDto，他们拥有相同的SpecialId和不同的ComicId
        ResultSupport<List<JumpDto>> jumpListByQuery = jumpService.getJumpListByQuery(jumpDto);
        if (!jumpListByQuery.isSuccess()) {
            return jumpListByQuery.castToReturnFailed(JumpDto.class);
        }
        List<JumpDto> jumpDtos = jumpListByQuery.getModule();
        //遍历获取idList
        for (JumpDto jumpDto1 : jumpDtos) {
            idList.add(jumpDto1.getComicId());
        }
        ResultSupport<List<ComicDto>> comicListByIdList = comicService.getComicListByIdList(idList);
        if (!comicListByIdList.isSuccess()) {
            return comicListByIdList.castToReturnFailed(JumpDto.class);
        }
        List<ComicDto> comicDtos = comicListByIdList.getModule();
        JumpDto jumpDto1 = new JumpDto();
        Map<SpecialDto, List<ComicDto>> map = new ConcurrentHashMap<>();
        map.put(module, comicDtos);
        jumpDto1.setJump(map);
        return ResultSupport.getInstance(true, "[关联查询成功]", jumpDto1);
    }


}
