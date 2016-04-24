package io.loli.box.dao;

import io.loli.box.entity.IdSeq;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author choco
 */
public interface IdSeqRepository extends JpaRepository<IdSeq, Long> {
    IdSeq save(IdSeq entity);
}
