////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 17.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.restapi.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thepanday.informatikproject.restapi.WebConstants;
import org.thepanday.informatikproject.restapi.entity.RequestFormDataEntity;
import org.thepanday.informatikproject.restapi.entity.WebResponse;

/**
 *
 */
@RestController
@RequestMapping("/predict")
public class ApplicationController {

    @RequestMapping(value = "/formInputProcess", method = RequestMethod.GET)
    public WebResponse predict(
        @RequestParam(value = WebConstants.REQUEST_FORM_VALUE)
            String homeTeam,
        @RequestBody
            RequestFormDataEntity request) {
        final WebResponse response = new WebResponse();
        response.setMessage("Not implemented yet! ;)");

        return response;
    }
}
