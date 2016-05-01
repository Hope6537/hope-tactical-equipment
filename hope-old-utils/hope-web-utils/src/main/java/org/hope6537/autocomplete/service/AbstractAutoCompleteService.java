package org.hope6537.autocomplete.service;

import org.hope6537.autocomplete.model.AutoComplete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自动提示的抽象类
 *
 * @author hope6537
 */
public abstract class AbstractAutoCompleteService implements AutoCompleteService {

    private Map<String, List<AutoComplete>> autoCompleteMap;

    public void initAutoComplete() {
        initAutoCompleteMap();
    }

    /**
     * 初始化所有的AutoComplete的list
     */
    abstract protected void initAutoCompleteMap();


    /**
     * 获取自动提示的结果
     *
     * @param autoCompleteParam
     * @return
     */
    public List<AutoComplete> getAutoCompleteResultList(AutoComplete autoCompleteParam) {
        return getMatchAutoCompleteList(autoCompleteParam, getAllAutoCompleteList(autoCompleteParam));
    }

    /**
     * 获取该flag下的list，包含全部信息
     *
     * @param autoCompleteParam
     * @return
     */
    private List<AutoComplete> getAllAutoCompleteList(AutoComplete autoCompleteParam) {
        if (getAutoCompleteMap() == null) {
            initAutoComplete();
        }
        return getAutoCompleteMap().get(autoCompleteParam.getFlag());
    }

    /**
     * 获取该flag下的匹配的list，默认多于10条的显示10条
     *
     * @param autoCompleteParam
     * @return
     */
    protected List<AutoComplete> getMatchAutoCompleteList(AutoComplete autoCompleteParam, List<AutoComplete> autoCompleteList) {
        List<AutoComplete> autoCompleteResultList = new ArrayList<>();
        if (autoCompleteList == null || autoCompleteList.size() <= 0) {
            return autoCompleteList;
        }
        for (AutoComplete autoCompleteVO : autoCompleteList) {
            if (!autoCompleteParam.isShowAll() && autoCompleteResultList.size() >= 10) {
                break;
            }
            if (autoCompleteVO.contain(autoCompleteParam.getKeyword())) {
                autoCompleteResultList.add(autoCompleteVO);
            }
        }
        return autoCompleteResultList;
    }

    public Map<String, List<AutoComplete>> getAutoCompleteMap() {
        return autoCompleteMap;
    }

    public void setAutoCompleteMap(Map<String, List<AutoComplete>> autoCompleteMap) {
        this.autoCompleteMap = autoCompleteMap;
    }
}
