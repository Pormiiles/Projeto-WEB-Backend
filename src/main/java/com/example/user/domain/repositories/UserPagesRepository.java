package com.example.user.domain.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.user.domain.models.User;

@Repository
// Repositório específico para realizar a paginação de dados na aplicação
public interface UserPagesRepository extends PagingAndSortingRepository<User, Integer> {
    
}
