package com.vinteo.inventory.repository;

import com.vinteo.inventory.entity.AuthorityEntity;
import com.vinteo.inventory.entity.UserEntity;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Repository
public class UserRepository {

    private UserDetailRepository userDetailRepository;
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    public void setUserDetailRepository(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }

    @Autowired
    public void setUserAuthorityRepository(UserAuthorityRepository userAuthorityRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
    }

    public User getByLogin(String login) throws AuthException {
        UserEntity user = userDetailRepository.findByUsername(login);
        if (user != null) {
            List<AuthorityEntity> userAuthorities = userAuthorityRepository.findByUsername(user.getUsername());
            Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (AuthorityEntity userAuthority : userAuthorities) {
                grantedAuthorities.add(new SimpleGrantedAuthority(userAuthority.getAuthority()));
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            throw new AuthException("Пользователь не найден");
        }
    }
}
