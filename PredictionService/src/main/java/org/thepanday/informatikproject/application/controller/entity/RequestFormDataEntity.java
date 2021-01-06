////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 17.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.controller.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 */
@Getter
@Setter
@Accessors(prefix = "m")
public class RequestFormDataEntity {
    private String mHomeTeam;
    private String mAwayTeam;
    private String mLeague;
}
