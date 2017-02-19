package com.javacodegeeks.examples.repositories;

import com.javacodegeeks.examples.entities.Assessee;
import com.javacodegeeks.examples.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface AssesseeRepository extends PagingAndSortingRepository<Assessee, Integer> {

}
