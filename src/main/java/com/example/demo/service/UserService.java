package com.example.demo.service;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.RegisterDTO;
import com.example.demo.model.Users;

import java.util.Map;


public interface UserService {
    void registerByEmail(RegisterDTO registerDTO);

    Map<String, String> login(LoginDTO loginDTO);

    void updateUserInfo(Map<String, String> updateInfo);

    void updatePsd(String psd);

    void logout();

    void logoff();


}
