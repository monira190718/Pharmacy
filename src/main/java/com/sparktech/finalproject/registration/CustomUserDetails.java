package com.sparktech.finalproject.registration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user){
        this.user = user;
    }


    public String getFullname(){
        return user.getFullname();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return java.util.List.of(
                new org.springframework.security.core.authority.SimpleGrantedAuthority(
                        "ROLE_" + user.getRole()
                )
        );
    }


    @Override
    public String getPassword(){
        return user.getPassword();
    }


    @Override
    public String getUsername(){
        return user.getEmail();
    }


    @Override
    public boolean isAccountNonExpired(){
        return true;
    }


    @Override
    public boolean isAccountNonLocked(){
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }


    @Override
    public boolean isEnabled(){
        return true;
    }
}