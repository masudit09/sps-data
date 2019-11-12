package com.javacodegeeks.examples.repositories;

import com.javacodegeeks.examples.entities.Content;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContetRepository extends PagingAndSortingRepository<Content, Integer> {

}
