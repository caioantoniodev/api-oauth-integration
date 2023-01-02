package com.workzone.apioauthintegration.application;

public interface IAuthenticationService {
    String retrieveClientId();

    String retrieveClientSecret();

    String retrieveAccessToken();
}
