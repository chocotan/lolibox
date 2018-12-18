package io.loli.box;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author choco
 */
@ConfigurationProperties("admin")
@Component
public class AdminProperties {
    @NotNull
    private String email;

    private boolean anonymous = false;

    private boolean signupInvitation = true;

    private Integer invitationLimitDays = 7;

    private String imgHost;
    private String cdnHost;

    private String httpsHost;

    public String getHttpsHost() {
        return httpsHost;
    }

    public void setHttpsHost(String httpsHost) {
        this.httpsHost = httpsHost;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isSignupInvitation() {
        return signupInvitation;
    }

    public void setSignupInvitation(boolean signupInvitation) {
        this.signupInvitation = signupInvitation;
    }

    public Integer getInvitationLimitDays() {
        return invitationLimitDays;
    }

    public void setInvitationLimitDays(Integer invitationLimitDays) {
        this.invitationLimitDays = invitationLimitDays;
    }

    public String getCdnHost() {
        return cdnHost;
    }

    public void setCdnHost(String cdnHost) {
        this.cdnHost = cdnHost;
    }

    public String getImgHost() {
        return imgHost;
    }

    public void setImgHost(String imgHost) {
        this.imgHost = imgHost;
    }
}
