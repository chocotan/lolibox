package io.loli.box.service.impl;

import io.loli.box.service.InvitationCodeService;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author choco
 */
@Service
public class InvitationCodeServiceImpl implements InvitationCodeService {

    @Autowired(required = false)
    @Qualifier("invitationCodeHashIds")
    private Hashids hashids;

    public String generate(String email) {
        return hashids.encode(Math.abs(email.hashCode()), System.currentTimeMillis());
    }

    public boolean verify(String email, String verificationCode) {
        long decoded[] = hashids.decode(verificationCode);
        if (decoded.length == 2) {
            return decoded[0] == Math.abs(email.hashCode()) && (System.currentTimeMillis() - (3600 * 1000 * 24 * 7) < decoded[1]);
        }
        return false;
    }
}
