package com.sps.data.repositories;

import com.sps.data.entities.Paragraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParagraphRepository extends BaseRepository<Paragraph> {

    @Query("SELECT x FROM Paragraph x WHERE x.section.chapter.id = :chapterId order by x.section.serial asc, x.serial asc")
    public List<Paragraph> findByChapterId( @Param("chapterId") Long chapterId);

    @Query("SELECT Max(x.serial) FROM Paragraph x WHERE x.section.id = :sectionId")
    public Integer findMaxParagraphSerial( @Param("sectionId") Long sectionId);

}
