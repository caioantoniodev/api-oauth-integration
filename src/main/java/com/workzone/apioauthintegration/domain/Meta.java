package com.workzone.apioauthintegration.domain;

import lombok.Data;

@Data
public class Meta {
    private Integer totalRecords;
    private Integer totalPages;
    private String requestDateTime;
}