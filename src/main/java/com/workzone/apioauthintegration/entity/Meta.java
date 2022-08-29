package com.workzone.apioauthintegration.entity;

import lombok.Data;

@Data
public class Meta {
    private Integer totalRecords;
    private Integer totalPages;
    private String requestDateTime;
}