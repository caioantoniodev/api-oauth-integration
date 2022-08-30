package com.workzone.apioauthintegration.domain;

import lombok.Data;

@Data
public class Links {

    private String self;
    private String first;
    private String prev;
    private String next;
    private String last;
}