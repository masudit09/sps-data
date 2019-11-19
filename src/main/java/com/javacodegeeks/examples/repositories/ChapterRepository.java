package com.javacodegeeks.examples.repositories;

import com.javacodegeeks.examples.entities.Chapter;
import com.javacodegeeks.examples.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long>, JpaSpecificationExecutor<Chapter> {

}
