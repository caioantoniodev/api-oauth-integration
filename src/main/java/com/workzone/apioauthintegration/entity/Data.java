package com.workzone.apioauthintegration.entity;

import java.util.List;

@lombok.Data
public class Data {
    private String consentId;
    private String creationDateTime;
    private String status;
    private String statusUpdateDateTime;
    private List<String> permissions;
    private String expirationDateTime;
    private String transactionFromDateTime;
    private String transactionToDateTime;
}