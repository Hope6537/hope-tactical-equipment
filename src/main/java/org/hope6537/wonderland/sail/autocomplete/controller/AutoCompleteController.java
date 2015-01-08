package org.hope6537.wonderland.sail.autocomplete.controller;

import org.hope6537.wonderland.sail.autocomplete.model.AutoComplete;
import org.hope6537.wonderland.sail.autocomplete.service.AutoCompleteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * AutoCompleteController
 *
 * @author gaoxinyu
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
