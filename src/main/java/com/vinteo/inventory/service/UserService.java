package com.vinteo.inventory.service;

import com.vinteo.inventory.entity.UserEntity;
import com.vinteo.inventory.repository.UserAuthorityRepository;
import com.vinteo.inventory.repository.UserDetailRepository;
import com.vinteo.inventory.repository.UserRepository;
import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@SessionScope
public class UserService implements UserDetailsService {
    private UserDetailRepository userDetailRepository;
    private UserRepository userRepository;
    private UserAuthorityRepository userAuthorityRepository;
    private UserEntity user;

    @Autowired
    public void setUserDetailRepository(UserDetailRepository userRepository) {
        this.userDetailRepository = userRepository;
    }

    @Autowired
    public void setUserAuthorityRepository(UserAuthorityRepository userAuthorityRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUser(String username) {
        if (user == null) {
            user = userDetailRepository.findByUsername(username);
            user.setAuthorities(userAuthorityRepository.findByUsername(username));
            return user;
        } else {
            return user;
        }
    }

    public User getByLogin(String login) throws AuthException {
        return this.userRepository.getByLogin(login);
    }

    public List<UserEntity> getAllUsers() {
        return userDetailRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.info("Loading user: " + login);
        try {
            User user = getByLogin(login);
            if (Objects.isNull(user)) {
                throw new UsernameNotFoundException(String.format("User %s is not found", login));
            }
            return user;
        } catch (AuthException e) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
    }


}
