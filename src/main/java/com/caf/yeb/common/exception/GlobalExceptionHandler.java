package com.caf.yeb.common.exception;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.caf.yeb.common.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @version 1.0
 * @date 2021/4/22 4:17 下午
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public R<String> handleHttpMethodNotSupportException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持Http请求方式错误：{}", LogUtils.printTop8StackTrace(e));
        return R.error();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public R<String> handleHttpMethodNotSupportException(HttpMessageNotReadableException e) {
        log.error("请求参数解析错误：{}", LogUtils.printTop8StackTrace(e));
        return R.error();
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public R<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("不合法的参数异常：{}", LogUtils.printTop8StackTrace(e));
        return R.error();
    }

     @ExceptionHandler({MethodArgumentNotValidException.class})
     public R<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
         this.response.setStatus(200);
         log.error("方法参数校验错误：{}", LogUtils.printTop8StackTrace(e));
         return this.assembleMsg(e.getBindingResult().getFieldErrors());
     }

     @ExceptionHandler({BindException.class})
     public R<String> bindException(BindException e) {
         List<FieldError> fieldErrors = e.getFieldErrors();
         this.response.setStatus(200);
         log.error("参数绑定错误：{}", LogUtils.printTop8StackTrace(e));
         return this.assembleMsg(fieldErrors);
     }

     @ExceptionHandler({MissingServletRequestParameterException.class})
     public R<String> missingServletRequestParameterException(MissingServletRequestParameterException e) {
         this.response.setStatus(200);
         String name = e.getParameterName();
         Map<String, String> msgMap = Maps.newHashMap(name, "参数不能为空");
         log.error("缺少请求参数错误：{}", LogUtils.printTop8StackTrace(e));
         return R.paramError(msgMap);
    }

     @ExceptionHandler({BadSqlGrammarException.class})
     @ResponseStatus(HttpStatus.OK)
     public void test(BadSqlGrammarException e) {
         log.error("bad sql {} ", e.getMessage());
     }

     @ExceptionHandler({ConstraintViolationException.class})
     public R<String> constraintViolationException(ConstraintViolationException e) {
         this.response.setStatus(200);
         Map<String, String> msgMap = new HashMap<>();
         e.getConstraintViolations().forEach(v -> {
             String name = v.getPropertyPath().toString().split("\\.")[1];
             String msg = v.getMessageTemplate();
             msgMap.put(name, msg);
         });
         log.error("参数约束错误：{}", LogUtils.printTop8StackTrace(e));
         return R.paramError(msgMap);
     }

     @ExceptionHandler({BusinessException.class})
     public R<String> businessException(BusinessException e) {
         log.error("业务捕获异常: {}={}", e.getCode(), LogUtils.printTop8StackTrace(e));
         return R.error(e.getMessage());
     }

    @ExceptionHandler({Exception.class})
    public R<String> handle(Exception e) {
        this.response.setStatus(200);
        log.error("代码执行报错：{}", LogUtils.printTop8StackTrace(e));
        return R.error();
    }

    private static final String GET = "GET";
    private static final String POST = "POST";

     private R<String> assembleMsg(List<FieldError> fieldErrors) {
         if (StringUtils.equals(this.request.getMethod(), GET)) {
             return R.error(((FieldError) fieldErrors.get(0)).getDefaultMessage());
         } else if (StringUtils.equals(this.request.getMethod(), POST) && !CollectionUtils.isEmpty(fieldErrors)) {
             Map<String, String> msgMap = new HashMap<>();
             fieldErrors.forEach((fieldError) -> {
                 String var10000 = (String) msgMap.put(fieldError.getField(), fieldError.getDefaultMessage());
             });
             return R.paramError(msgMap);
         } else {
             return null;
         }
     }
}
