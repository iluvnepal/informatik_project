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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thepanday.informatikproject.application.controller.form.RequestFormModel;

@Controller
@EnableWebMvc
public class WebInterfaceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebInterfaceController.class);

    @GetMapping(value = "/")
    public ModelAndView showForm() {
        LOGGER.info("Get request.");
        final ModelAndView requestForm = new ModelAndView("html/requestform");
        requestForm.addObject("requestform", new RequestFormModel());
        return requestForm;
    }

    @PostMapping(value = "/submitTeams")
    public ModelAndView submit(
        @ModelAttribute
            RequestFormModel formData, BindingResult result, Model model) {

        LOGGER.info("League: {}, TeamA: {}, TeamB: {}", formData.getLeague(), formData.getHomeTeam(), formData.getAwayTeam());

        if (result.hasErrors()) {
            LOGGER.warn(result
                            .getAllErrors()
                            .toString());
            return new ModelAndView("error");
        }
        ModelAndView mav = new ModelAndView("html/submittedDataView");
        mav.addObject("formData", formData);

        return mav;
    }

//    @GetMapping(value = "/getLeagueTeams")
//    public String getLeagueTeams() {
//        return Json
//    }

}
