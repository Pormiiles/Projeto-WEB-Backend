package com.example.user.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.user.models.User;

@Repository
public interface UserPagesRepository extends PagingAndSortingRepository<User, Integer> {
    
}
