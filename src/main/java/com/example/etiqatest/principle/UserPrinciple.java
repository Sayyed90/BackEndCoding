package com.example.etiqatest.principle;


import com.example.etiqatest.entity.Roles;
import com.example.etiqatest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserPrinciple implements UserDetails {

    @Autowired
    private User user;

    public UserPrinciple(User user) {
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<Roles> role=user.getRoles();
        role.forEach(nm->{
            grantedAuthorities.add(new SimpleGrantedAuthority(nm.getRole()));
        });
         return grantedAuthorities;
      }
    public long getId(){return user.getId();}

    public Set<Roles> getRoles(){return user.getRoles();}
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
