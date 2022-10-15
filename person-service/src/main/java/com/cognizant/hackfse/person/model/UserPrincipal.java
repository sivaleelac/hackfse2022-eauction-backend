package com.cognizant.hackfse.person.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.hackfse.person.entity.PersonEntity;

public class UserPrincipal implements UserDetails {

    private String email;
    private String password;

    public UserPrincipal(String email,String password,Collection<? extends GrantedAuthority> authorities){
        this.email=email;
        this.password=password;
        this.authorities=authorities;
    }

    private Collection<? extends GrantedAuthority> authorities;

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
        return email;
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
    public static UserPrincipal create(PersonEntity personEntity) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UserPrincipal(personEntity.getEmailAddress(),personEntity.getPassword(),authorities);
    }
}
