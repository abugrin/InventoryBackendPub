package com.vinteo.inventory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
public class AuthorityEntity implements GrantedAuthority {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "username", nullable = false)
    private String username;

    @Setter
    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public AuthorityEntity() {
    }

    public AuthorityEntity(String authority) {
        this.authority = authority;
    }
}