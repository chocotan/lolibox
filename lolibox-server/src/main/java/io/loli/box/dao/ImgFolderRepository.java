package io.loli.box.dao;

import io.loli.box.entity.ImgFolder;
import io.loli.box.entity.ImgFolderPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * @author choco
 */
public interface ImgFolderRepository extends JpaRepository<ImgFolder, ImgFolderPk> {
    public ImgFolder save(ImgFolder folder);

    @Query("from ImgFolder f where f.id.year=?")
    public List<ImgFolder> findByYear(int year);

    @Transactional
    @Transient
    public default ImgFolder getCurrentFolder() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        ImgFolderPk pk = new ImgFolderPk();
        pk.setYear(year);
        pk.setMonth(month);
        pk.setDay(day);
        ImgFolder folder = this.findOne(pk);
        if (folder == null) {
            folder = new ImgFolder();
            folder.setId(pk);
            this.save(folder);
        }
        return folder;

    }
    @Query("from ImgFolder f where f.id.year=? and f.id.month=?")
    public List<ImgFolder> findByYearAndMonth(int year, int month);
}