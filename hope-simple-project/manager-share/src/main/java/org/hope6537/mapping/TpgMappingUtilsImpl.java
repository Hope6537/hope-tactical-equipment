package org.hope6537.mapping;

import org.dozer.Mapper;
import org.hope6537.basic.dataobject.BasicDO;
import org.hope6537.basic.dto.BasicDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by wuyang.zp on 2015/7/20.
 */
public class TpgMappingUtilsImpl implements TpgMappingUtils {


    private static final Logger logger = LoggerFactory.getLogger(TpgMappingUtils.class);

    @Autowired
    @Qualifier(value = "tpgMappingBean")
    private Mapper tpgMapping;

    @Override
    public <T> T doMap(Object source, Class<T> destinationClass) {
        if (source == null || destinationClass == null) {
            return null;
        }
        T t;
        try {
            t = destinationClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        getTpgMapping().map(source, t);
        /**
         * BasicDO: offset = a  = BasicDto : startRecord
         * BasicDO: limit  = b = BasicDto : pageSize
         */
        // dto->do handle pagination arguments
        if (source instanceof BasicDto && BasicDO.class.isAssignableFrom(destinationClass)) {
            BasicDto pageDto = (BasicDto) source;
            if (pageDto.getStartRecord() != null && pageDto.getPageSize() != null) {
                BasicDO pageDo = (BasicDO) t;
                pageDo.setOffset(pageDto.getStartRecord());
                pageDo.setLimit(pageDto.getPageSize());
            }
        }
        // do->dto
        if (source instanceof BasicDO && BasicDto.class.isAssignableFrom(destinationClass)) {
            BasicDO pageDo = (BasicDO) source;
            if (pageDo.getLimit() != null && pageDo.getOffset() != null) {
                BasicDto pageDto = (BasicDto) t;
                pageDto.setCurrentPageNo((pageDo.getOffset() / pageDo.getLimit()) + 1);
                pageDto.setPageSize(pageDo.getLimit());
            }
        }
        return t;
    }

    public Mapper getTpgMapping() {
        return tpgMapping;
    }

    public void setTpgMapping(Mapper tpgMapping) {
        this.tpgMapping = tpgMapping;
    }
}
