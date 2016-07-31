package io.loli.box.oauth2;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author choco
 */
@Entity
public class OauthRefreshToken {
    @Id
    private String tokenId;
    private String token;
    private String authentication;

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
