package com.example.demo.config;

import com.example.demo.util.MyPassword;
import com.example.demo.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.demo.util.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用方法级别权限控制功能
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        //.requestMatchers("/test/login2").permitAll()//允许/mail/**路径下的所有请求访问，无需进行鉴权认证
                        .requestMatchers("/test/login").hasAuthority("ROLE_USER")
                        .anyRequest().authenticated()//其他所有请求都需要进行鉴权认证
                )

                .logout(LogoutConfigurer::permitAll);
        //把token校验过滤器添加到过滤器链中
        http.addFilterAfter(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//                .exceptionHandling()
//                .authenticationEntryPoint(globalExceptionHandler)
//                .accessDeniedHandler(globalExceptionHandler);
//

        return http.build();
    }
        // 禁用HTTP响

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        System.out.println("passwordEncoder");
        return new MyPassword();
        //return new BCryptPasswordEncoder();
    }
//    @Bean
//    public WebSecurityCustomizer ignoringCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/**");
//    }

}

