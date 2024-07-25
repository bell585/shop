package com.example.demo.execption;

import com.alibaba.fastjson.JSON;
import com.example.demo.comment.Result;
import com.example.demo.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.time.LocalDateTime;

 //implements AuthenticationEntryPoint,AccessDeniedHandler implements HandlerExceptionResolver
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleCustomException(MyException ex) {
        System.out.println(LocalDateTime.now());
        logger.error(ex.getMessage());
        return Result.success(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<?> NoHandlerFoundException(Exception ex) {
        return Result.success(404, "404异常", LocalDateTime.now());
    }

    @ExceptionHandler(NullPointerException.class)  //使用枚举类的异常处理方式
    public Result<?> handleNullPointerException(NullPointerException ex) {
        return Result.success(1001, "空指针异常", LocalDateTime.now());
        // return new ErrorResponse(800, "NullPointer", LocalDateTime.now());
    }

    //捕获@RequestBody类型参数触发校验规则抛出的异常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<?> handleValidException() {
        return Result.success(1002, "参数校验未通过", LocalDateTime.now());
    }

    //捕获@RequestParam/@PathVariable参数触发校验规则抛出的异常
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException() {
        return Result.success(1003, "参数校验未通过", LocalDateTime.now());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public Result<?> BadCredentialsException() {
        return Result.success(1004, "用户名或密码错误",LocalDateTime.now());
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> HttpRequestMethodNotSupportedException() {
        return Result.success(1005, "未登录用户，请重新登录",LocalDateTime.now());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> IllegalArgumentException(IllegalArgumentException ex) {
        System.out.println("已调用自定义IllegalArgumentException");
        return Result.success(1006, "未登录用户，请重新登录",LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleGenericException(Exception ex) {
        return Result.success(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", LocalDateTime.now());

    }

  /*   @Override
     public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
         // 创建自定义的响应对象
         Result<?> result = new Result(1006, "未登录用户，请重新登录", LocalDateTime.now());

         // 设置响应的内容类型和状态码
         response.setContentType("application/json");
         response.setStatus(HttpServletResponse.SC_OK);

         // 将响应对象转换为JSON字符串并写入响应
         try {
             response.getWriter().write(JSON.toJSONString(result));
         } catch (IOException e) {
             System.out.println("nihao");
             e.printStackTrace();
         }

         // 返回一个空的ModelAndView，表示已经处理完异常，不需要进行视图渲染
         return new ModelAndView();
     }

   @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 处理认证异常
        Result<?> result = new Result<>(1007,"未登录用户，请重新登录",LocalDateTime.now());
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response, json);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 处理访问被拒绝异常
        Result<?> result = new Result<>(1008,"未登录用户，请重新登录",LocalDateTime.now());
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response, json);
    }




@ExceptionHandler(NullPointerException.class)  //使用枚举类的异常处理方式
    public ErrorResponse handleNullPointerException(NullPointerException ex) {
        return new ErrorResponse(INVALID_REQUEST.getCode(), INVALID_REQUEST.getMessage(), LocalDateTime.now());
    }
     */

}
