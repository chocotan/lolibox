package io.loli.box.dao;

import io.loli.box.entity.PersistentLogins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author choco
 */
public interface JpaReMemberMeRepository extends JpaRepository<PersistentLogins, String>, PersistentTokenRepository {

    @Modifying
    @Transactional
    default void createNewToken(PersistentRememberMeToken token) {
        PersistentLogins logins = new PersistentLogins();
        logins.setLastUsed(token.getDate());
        logins.setSeries(token.getSeries());
        logins.setToken(token.getTokenValue());
        logins.setUsername(token.getUsername());
        this.save(logins);
    }

    @Modifying
    @Transactional
    default void updateToken(String series, String tokenValue, Date lastUsed) {
        updateTokenAndLastUsedBySeries(tokenValue, lastUsed, series);
    }

    @Query("update PersistentLogins set token=?, lastUsed=? where series=?")
    @Modifying
    public int updateTokenAndLastUsedBySeries(String token, Date lastUsed, String series);

    public List<PersistentLogins> findBySeries(String series);

    default PersistentRememberMeToken getTokenForSeries(String seriesId) {
        List<PersistentLogins> logins = this.findBySeries(seriesId);
        if (logins.isEmpty()) {
            return null;
        }
        PersistentLogins login = logins.get(0);
        return new PersistentRememberMeToken(login.getUsername(), login.getSeries(), login.getToken(), login.getLastUsed());
    }

    @Modifying
    public int deleteByUsername(String username);

    @Transactional
    default void removeUserTokens(String username){
        this.deleteByUsername(username);
    }
}
