package io.loli.box.dao;

import io.loli.box.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author choco
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
