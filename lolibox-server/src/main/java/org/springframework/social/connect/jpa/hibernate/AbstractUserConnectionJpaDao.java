//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.social.connect.jpa.hibernate;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.jpa.JpaTemplate;
import org.springframework.social.connect.jpa.RemoteUser;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.Map.Entry;

public abstract class AbstractUserConnectionJpaDao<U extends AbstractUserConnection<?>> implements JpaTemplate {
    private Class<U> persistentClass;
    @PersistenceContext
    private EntityManager entityManager;

    public Class<U> getPersistentClass() {
        return this.persistentClass;
    }

    public AbstractUserConnectionJpaDao(Class<U> persistentClass) {
        this.persistentClass = persistentClass;
    }

    protected String getProviderIdJpql() {
        return "u.primaryKey.providerId";
    }

    protected String getUserIdJpql() {
        return "u.primaryKey.userId";
    }

    protected String getProviderUserIdJpql() {
        return "u.primaryKey.providerUserId";
    }

    @Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED
    )
    public Set<String> findUsersConnectedTo(String providerId, Set<String> providerUserIds) {
        TypedQuery query = this.entityManager.createQuery("select " + this.getUserIdJpql() + " from UserConnection u where " + this.getProviderIdJpql() + " = :providerId and " + this.getProviderUserIdJpql() + " in (:providerUserIds)", String.class);
        query.setParameter("providerId", providerId);
        query.setParameter("providerUserIds", providerUserIds);
        List users = query.getResultList();
        HashSet userIds = new HashSet();
        Iterator i$ = users.iterator();

        while(i$.hasNext()) {
            String userId = (String)i$.next();
            if(!userIds.contains(userId)) {
                userIds.add(userId);
            }
        }

        return userIds;
    }

    @Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED
    )
    public List<RemoteUser> getPrimary(String userId, String providerId) {
        TypedQuery query = this.entityManager.createQuery("select u from UserConnection u where " + this.getUserIdJpql() + " = :userId and " + this.getProviderIdJpql() + " = :providerId order by u.rank", RemoteUser.class);
        query.setParameter("userId", userId);
        query.setParameter("providerId", providerId);
        return query.getResultList();
    }

    @Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED
    )
    public int getRank(String userId, String providerId) {
        TypedQuery query = this.entityManager.createQuery("select max(u.rank) from UserConnection u where " + this.getUserIdJpql() + "= :userId and " + this.getProviderIdJpql() + " = :providerId", Integer.class);
        query.setParameter("userId", userId);
        query.setParameter("providerId", providerId);
        Integer result = (Integer)query.getSingleResult();
        return result == null?1:result.intValue() + 1;
    }

    @Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED
    )
    public List<RemoteUser> getAll(String userId, MultiValueMap<String, String> providerUsers) {
        ArrayList userList = new ArrayList();
        Iterator i$ = providerUsers.entrySet().iterator();

        while(i$.hasNext()) {
            Entry entry = (Entry)i$.next();
            TypedQuery query = this.entityManager.createQuery("select u from UserConnection u where " + this.getUserIdJpql() + " = :userId and " + this.getProviderIdJpql() + "= :providerId and " + this.getProviderUserIdJpql() + " in (:providerUserIds) order by u.rank", RemoteUser.class);
            query.setParameter("userId", userId);
            query.setParameter("providerId", entry.getKey());
            query.setParameter("providerUserIds", entry.getValue());
            userList.addAll(query.getResultList());
        }

        return userList;
    }

    @Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED
    )
    public List<RemoteUser> getAll(String userId) {
        TypedQuery query = this.entityManager.createQuery("select u from UserConnection u where " + this.getUserIdJpql() + " = :userId order by u.rank", RemoteUser.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED
    )
    public List<RemoteUser> getAll(String userId, String providerId) {
        TypedQuery query = this.entityManager.createQuery("select u from UserConnection u where " + this.getUserIdJpql() + " = :userId and " + this.getProviderIdJpql() + "= :providerId order by u.rank", RemoteUser.class);
        query.setParameter("userId", userId);
        query.setParameter("providerId", providerId);
        return query.getResultList();
    }

    @Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED
    )
    public RemoteUser get(String userId, String providerId, String providerUserId) {
        TypedQuery query = this.entityManager.createQuery("select u from UserConnection u where " + this.getUserIdJpql() + " = :userId and " + this.getProviderIdJpql() + "= :providerId and " + this.getProviderUserIdJpql() + " = :providerUserId", RemoteUser.class);
        query.setParameter("userId", userId);
        query.setParameter("providerId", providerId);
        query.setParameter("providerUserId", providerUserId);
        List userList = query.getResultList();
        if(userList.size() == 0) {
            throw new EmptyResultDataAccessException(1);
        } else {
            return (RemoteUser)userList.get(0);
        }
    }

    @Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED
    )
    public List<RemoteUser> get(String providerId, String providerUserId) {
        TypedQuery query = this.entityManager.createQuery("select u from UserConnection u where " + this.getProviderIdJpql() + " = :providerId and " + this.getProviderUserIdJpql() + " = :providerUserId order by u.rank", RemoteUser.class);
        query.setParameter("providerId", providerId);
        query.setParameter("providerUserId", providerUserId);
        return query.getResultList();
    }

    @Transactional(
        readOnly = false,
        propagation = Propagation.REQUIRED
    )
    public void remove(String userId, String providerId) {
        Iterator i$ = this.getAll(userId, providerId).iterator();

        while(i$.hasNext()) {
            RemoteUser remoteUser = (RemoteUser)i$.next();
            this.entityManager.remove(remoteUser);
        }

    }

    @Transactional(
        readOnly = false,
        propagation = Propagation.REQUIRED
    )
    public void remove(String userId, String providerId, String providerUserId) {
        try {
            this.entityManager.remove(this.get(userId, providerId, providerUserId));
        } catch (EmptyResultDataAccessException var5) {
            ;
        }

    }

    protected abstract U createNewUserConnection(String var1, String var2, String var3, int var4, String var5, String var6, String var7, String var8, String var9, String var10, Long var11);

    protected void setDefaultProperties(U userConnection, String userId, String providerId, String providerUserId, int rank, String displayName, String profileUrl, String imageUrl, String accessToken, String secret, String refreshToken, Long expireTime) {
        userConnection.setUserId(userId);
        userConnection.setProviderId(providerId);
        userConnection.setProviderUserId(providerUserId);
        userConnection.setRank(rank);
        userConnection.setDisplayName(displayName);
        userConnection.setProfileUrl(profileUrl);
        userConnection.setImageUrl(imageUrl);
        userConnection.setAccessToken(accessToken);
        userConnection.setSecret(secret);
        userConnection.setRefreshToken(refreshToken);
        userConnection.setExpireTime(expireTime);
    }

    @Transactional(
        readOnly = false,
        propagation = Propagation.REQUIRED
    )
    public RemoteUser createRemoteUser(String userId, String providerId, String providerUserId, int rank, String displayName, String profileUrl, String imageUrl, String accessToken, String secret, String refreshToken, Long expireTime) {
        AbstractUserConnection remoteUser = this.createNewUserConnection(userId, providerId, providerUserId, rank, displayName, profileUrl, imageUrl, accessToken, secret, refreshToken, expireTime);

        try {
            RemoteUser e = this.get(userId, providerId, providerUserId);
            if(e != null) {
                throw new DuplicateConnectionException(new ConnectionKey(providerId, providerUserId));
            }
        } catch (EmptyResultDataAccessException var14) {
            ;
        }

        this.save(remoteUser);
        return remoteUser;
    }

    @Transactional(
        readOnly = false,
        propagation = Propagation.REQUIRED
    )
    public RemoteUser save(RemoteUser user) {
        this.entityManager.merge(user);
        return user;
    }
}
