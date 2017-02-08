package com.javacodegeeks.examples.repositories;

import com.javacodegeeks.examples.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    @Query("SELECT x FROM User x WHERE x.username = :username ")
    public User findByUsername(@Param("username") String username);
	
}
