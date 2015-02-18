package org.hope6537.autocomplete.controller;

import org.hope6537.autocomplete.model.AutoComplete;
import org.hope6537.autocomplete.service.AutoCompleteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * AutoCompleteController
 *
 * @author hope6537
 */
@RequestMapping("/autoComplete")
public class AutoCompleteController {

    private AutoCompleteService autoCompleteService;

    public void setAutoCompleteService(AutoCompleteService autoCompleteService) {
        this.autoCompleteService = autoCompleteService;
    }

    @RequestMapping
    @ResponseBody
    public List<AutoComplete> autoComplete(AutoComplete autoCompleteParam) {
        return autoCompleteService.getAutoCompleteResultList(autoCompleteParam);
    }

}
