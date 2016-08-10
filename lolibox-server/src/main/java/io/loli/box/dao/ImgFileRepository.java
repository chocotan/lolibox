package io.loli.box.dao;

import io.loli.box.entity.ImgFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @author choco
 */
public interface ImgFileRepository extends JpaRepository<ImgFile, Long> {
    public ImgFile save(ImgFile file);

    int deleteByShortName(String name);

    ImgFile findByShortName(String name);

    @Transactional
    @Modifying
    @Query("update ImgFile u set u.delete=?2 where u.shortName=?1")
    void updateDeleteByShortName(String name, boolean i);

    Page<ImgFile> findByUserIdOrderByCreateDateDesc(Long userId, Pageable pageable);
}
