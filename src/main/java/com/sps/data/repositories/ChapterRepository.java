package com.sps.data.repositories;

import com.sps.data.entities.Chapter;
import com.sps.data.entities.Paragraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChapterRepository extends BaseRepository<Chapter> {
    @Query("SELECT x FROM Chapter x order by x.serial")
    public List<Chapter> findAllOrderByChapter();
}
