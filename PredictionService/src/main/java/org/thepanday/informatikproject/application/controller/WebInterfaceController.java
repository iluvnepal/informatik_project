////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 17.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thepanday.informatikproject.application.controller.form.IFormService;
import org.thepanday.informatikproject.application.controller.form.RequestFormModel;
import org.thepanday.informatikproject.application.service.ITrainingDataService;

@Controller
@EnableWebMvc
public class WebInterfaceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebInterfaceController.class);

    @Autowired
    private IFormService mFormService;

    @Autowired
    private ITrainingDataService mTrainingDataService;

    @GetMapping(value = "/")
    public ModelAndView showForm() {
        LOGGER.info("Initialising training data service.");
        mTrainingDataService.gatherAllTeamsDataAsynchronously();
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

    @GetMapping(value = "/getTeamsForLeague")
    @ResponseBody
    public String getTeamsForLeague() {
        ObjectMapper o = new ObjectMapper();
        try {
            return o.writeValueAsString(mFormService.getTeamsForAllLeague());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Got jackShit";
        }
    }

    @GetMapping(value = "/getLeagues")
    @ResponseBody
    public String getLeagues() {
        ObjectMapper o = new ObjectMapper();
        try {
            return o.writeValueAsString(mFormService.getLeagues());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Got jackShit";
        }
    }

    @GetMapping(value = "/getTrainingData")
    @ResponseBody
    public String getTrainingData() {
        final ObjectMapper o = new ObjectMapper();
        String valueAsString;
        try {
            valueAsString = o.writeValueAsString(mTrainingDataService.getTrainingData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Got jackShit!";
        }
        return valueAsString;
    }

    @GetMapping(value = "/getAverageForTeams")
    @ResponseBody
    public String getAverageForTeams(
        @RequestParam(name = "homeTeam")
            String homeTeamName,
        @RequestParam(name = "awayTeam")
            String awayTeamName) {
        return objectToJsonString(mTrainingDataService.getTrainingDataForTeams(homeTeamName, awayTeamName));
    }

    private String objectToJsonString(Object value) {
        final ObjectMapper o = new ObjectMapper();
        String valueAsString;
        try {
            valueAsString = o.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Could not parse object";
        }
        return valueAsString;
    }

}
