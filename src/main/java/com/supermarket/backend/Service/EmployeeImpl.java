package com.supermarket.backend.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supermarket.backend.Entity.EmployeeEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EmployeeImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;
    @Getter
    private final Integer id;
    @Getter
    private final String email;
    @JsonIgnore
    private final String password;

    public EmployeeImpl(Integer id, String username, String email, String password,
                        Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static EmployeeImpl build(EmployeeEntity user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new EmployeeImpl(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EmployeeImpl user = (EmployeeImpl) o;
        return Objects.equals(id, user.id);
    }
}
