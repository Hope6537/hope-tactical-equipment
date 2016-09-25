package org.hope6537.convert.impl;

import org.dozer.Mapper;
import org.hope6537.convert.MappingConverter;
import org.hope6537.page.PageDo;
import org.hope6537.page.PageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuyang.zp on 2015/7/20.
 */
public class DozerMappingConverter implements MappingConverter {

    private static final Logger logger = LoggerFactory.getLogger(MappingConverter.class);

    private Mapper mapper;

    /**
     * PageDo: offset = a  = PageDto : startRecord
     * PageDo: limit  = b = PageDto : pageSize
     */
    private static <T> void pageConvert(Object source, Class<T> destinationClass, T t) {
        // dto->do handle pagination arguments
        if (source instanceof PageDto && PageDo.class.isAssignableFrom(destinationClass)) {
            PageDto pageDto = (PageDto) source;
            if (pageDto.getStartRecord() != null && pageDto.getPageSize() != null) {
                PageDo pageDo = (PageDo) t;
                pageDo.setOffset(pageDto.getStartRecord());
                pageDo.setLimit(pageDto.getPageSize());
            }
        }
        // do->dto
        if (source instanceof PageDo && PageDto.class.isAssignableFrom(destinationClass)) {
            PageDo pageDo = (PageDo) source;
            if (pageDo.getLimit() != null && pageDo.getOffset() != null) {
                PageDto pageDto = (PageDto) t;
                pageDto.setCurrentPage((pageDo.getOffset() / pageDo.getLimit()) + 1);
                pageDto.setPageSize(pageDo.getLimit());
            }
        }
    }

    //@WatchedMethod
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
        getMapper().map(source, t);
        pageConvert(source, destinationClass, t);
        return t;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

}
