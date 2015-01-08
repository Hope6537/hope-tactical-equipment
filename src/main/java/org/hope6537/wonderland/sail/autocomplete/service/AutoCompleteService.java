package org.hope6537.wonderland.sail.autocomplete.service;


import org.hope6537.wonderland.sail.autocomplete.model.AutoComplete;

import java.util.List;

/**
 * 自动提示的抽象类
 *
 * @author gaoxinyu
 */
public interface AutoCompleteService {

    void initAutoComplete();

    List<AutoComplete> getAutoCompleteResultList(AutoComplete autoCompleteParam);

}
