package org.hope6537.bo;

import org.hope6537.dto.JumpDto;
import org.hope6537.entity.ResultSupport;

/**
 * Created by Dintama on 2016/3/13.
 */
public interface JumpBusiness {


    /**
     * 获取专辑内容-关联内容拼接
     *
     * @param query query-专辑ID
     * @return ResultSupport.module = 专辑
     * */
    ResultSupport<JumpDto> getJumpBySpecial(JumpDto query);

}
