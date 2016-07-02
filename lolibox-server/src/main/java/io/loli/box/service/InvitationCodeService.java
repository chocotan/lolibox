package io.loli.box.service;

/**
 * @author choco
 */
public interface InvitationCodeService {
    public String generate(String email);

    public boolean verify(String email, String verificationCode);
}
