package com.ezen.auth;

import java.util.ArrayList;
import java.util.Collection; 
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ezen.model.Users;

import lombok.Data;

@Data
public class MemberDetails implements UserDetails {

    private final Users users;

    public MemberDetails(Users users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       
        Collection<GrantedAuthority> collect = new ArrayList<>();
        
        collect.add(new SimpleGrantedAuthority(users.getRole().toString()));
       
        return collect;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
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
}