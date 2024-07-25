package com.example.demo.service.impl;

import com.example.demo.mapper.UsersMapper;
import com.example.demo.model.LoginUser;
import com.example.demo.model.Users;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersMapper.selectAllByEmailUsers(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        //System.out.println(new LoginUser(user.getUserid(), user.getUsername(), user.getPassword(), user.getEmail(), user.getUsertypeid()));
        return new LoginUser(user.getUserid(), user.getUsername(), user.getPassword(), user.getEmail(), user.getUsertypeid(),user.getCondition());
    }
}
