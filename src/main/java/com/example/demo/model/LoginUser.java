package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LoginUser implements UserDetails {

    private Long userid;
    private String username;

    private String password;
    private String email;
    private Integer usertypeid;
    private Integer condition;

    public LoginUser(Long userid, String username, String password, String email, Integer usertypeid, Integer condition) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.usertypeid = usertypeid;
        this.condition = condition;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        // 根据userTypeId添加相应的权限
        if (getUsertypeid() == 1) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (getUsertypeid() == 0) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

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

    public Long getUserid() {
        return userid;
    }

    public String getEmail() {
        return email;
    }

    public Integer getUsertypeid() {
        return usertypeid;
    }

    public Integer getCondition() {
        return condition;
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
        if (getCondition() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
