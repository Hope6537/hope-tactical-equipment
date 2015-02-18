package org.hope6537.autocomplete.service;


import org.hope6537.autocomplete.model.AutoComplete;

import java.util.List;

/**
 * 自动提示的抽象类
 *
 * @author hope6537
 */
public interface AutoCompleteService {

    void initAutoComplete();

    List<AutoComplete> getAutoCompleteResultList(AutoComplete autoCompleteParam);

}
