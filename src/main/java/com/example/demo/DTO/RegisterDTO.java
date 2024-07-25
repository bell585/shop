package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class RegisterDTO {
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")

    private String email;
    @NotBlank(message = "密码不能为空")
    @Length(min = 8,max = 16,message = "密码长度不符合要求")
    private String password1;
    @NotBlank(message = "密码不能为空")
    @Length(min = 8,max = 16,message = "密码长度不符合要求")
    private String password2;
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "\\d{6}", message = "Code必须是6位数字")
    private String code;


    public RegisterDTO() {
    }

    public RegisterDTO(String email, String password1, String password2, String code) {
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
        this.code = code;
    }

    /**
     * 获取
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取
     * @return password1
     */
    public String getPassword1() {
        return password1;
    }

    /**
     * 设置
     * @param password1
     */
    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    /**
     * 获取
     * @return password2
     */
    public String getPassword2() {
        return password2;
    }

    /**
     * 设置
     * @param password2
     */
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    /**
     * 获取
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    public String toString() {
        return "RegisterDTO{email = " + email + ", password1 = " + password1 + ", password2 = " + password2 + ", code = " + code + "}";
    }
}
