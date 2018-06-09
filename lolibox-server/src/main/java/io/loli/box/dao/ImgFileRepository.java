package io.loli.box.dao;

import io.loli.box.entity.ImgFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

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

    @Query(value = "select f.* " +
            "from img_file f " +
            "left join user_accounts u " +
            "on f.user_id = u.id " +
            "where u.id = ?1 and f.deleted = ?2 " +
            "order by f.create_date desc /*#pageable*/",
            countQuery = "select count(f.*) " +
                    "from img_file f " +
                    "left join user_accounts u " +
                    "on f.user_id = u.id " +
                    "where u.id = ?1 and f.deleted = ?2 ", nativeQuery = true)
    Page<ImgFile> findByUserIdAndDeleteOrderByCreateDate(Long userId, Boolean delete, Pageable pageable);

    List<ImgFile> findByGreenStatus(Integer greenStatus, Date from, Date end);


    @Transactional
    @Modifying
    @Query("update ImgFile u set u.greenStatus=?1 where u.id=?2")
    void updateGreenStatusById(Integer greenStatus, Long id);

    @Transactional
    @Modifying
    @Query("update ImgFile u set u.greenTaskId=?1 where u.id=?2")
    void updateTaskIdById(String taskId, Long id);

    @Transactional
    @Modifying
    @Query("update ImgFile u set u.greenPoint=?1 where u.id=?2")
    void updateGreenPointById(float checkResult, Long id);
}
