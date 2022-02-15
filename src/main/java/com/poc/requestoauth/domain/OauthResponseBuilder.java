package com.poc.requestoauth.domain;

public final class OauthResponseBuilder {
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;

    private OauthResponseBuilder() {
    }

    public static OauthResponseBuilder anOauthResponse() {
        return new OauthResponseBuilder();
    }

    public OauthResponseBuilder withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public OauthResponseBuilder withTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public OauthResponseBuilder withExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public OauthResponse build() {
        OauthResponse oauthResponse = new OauthResponse();
        oauthResponse.setAccessToken(accessToken);
        oauthResponse.setTokenType(tokenType);
        oauthResponse.setExpiresIn(expiresIn);
        return oauthResponse;
    }
}
