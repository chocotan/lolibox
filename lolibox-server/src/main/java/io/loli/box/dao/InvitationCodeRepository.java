package io.loli.box.dao;

import io.loli.box.entity.InvitationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author choco
 */
public interface InvitationCodeRepository extends JpaRepository<InvitationCode, Long> {
    public List<InvitationCode> findByCreateUserEmailOrderByCreateDateDesc(String email);

    public Optional<InvitationCode> findByCode(String code);

}
