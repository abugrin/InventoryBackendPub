package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);


}
