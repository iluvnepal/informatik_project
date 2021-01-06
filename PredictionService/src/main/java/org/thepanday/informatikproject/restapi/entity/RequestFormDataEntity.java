////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 17.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.restapi.entity;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
public class RequestFormDataEntity {
    private String mHomeTeam;
    private String mAwayTeam;
    private String mLeague;
}
