////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 17.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.thepanday.informatikproject.application.controller.entity.RequestFormDataEntity;

@Controller
public class WebInterfaceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebInterfaceController.class);

    private String teamA;
    private String teamB;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("index", "requestFormDataEntity", new RequestFormDataEntity());
    }

    @PostMapping("/submitTeams")
    public String submit(
        @ModelAttribute("requestFormDataEntity")
            RequestFormDataEntity formData, BindingResult result, ModelMap model) {
        LOGGER.info("League: {}, TeamA: {}, TeamB: {}", formData.getLeague(), formData.getHomeTeam(), formData.getAwayTeam());

        if (result.hasErrors()) {
            return "error";
        }

        model.addAttribute("mLeague", formData.getLeague());
        model.addAttribute("mHomeTeam", formData.getHomeTeam());
        model.addAttribute("mAwayTeam", formData.getAwayTeam());
        return "requestDataView";
    }

}
