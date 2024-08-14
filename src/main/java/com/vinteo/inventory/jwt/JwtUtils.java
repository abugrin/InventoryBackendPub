package com.vinteo.inventory.jwt;

import com.vinteo.inventory.entity.AuthorityEntity;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static HashSet<AuthorityEntity> getRoles(Claims claims) {
        @SuppressWarnings("unchecked")
        final List<LinkedHashMap<String, String>> roles = claims.get("roles", List.class);
        HashSet<AuthorityEntity> rolesSet = new HashSet<>();
        for (LinkedHashMap<String, String> role : roles) {
            //System.out.println(role.getClass().getName() + " Role: " + role.get("authority"));
            rolesSet.add(new AuthorityEntity(role.get("authority")));
        }
        return rolesSet;
    }

}