package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {

    public List<AuthorityEntity> findByUsername(String username);

}
