package com.atguigu.servicebase.exceptionhandler;


import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 王冰炜
 * @create 2021-04-06 20:15
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    //全局异常处理
    //指定何种异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody//它不在controller中，为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了自定义全局异常处理");
    }

    //特定异常（除数不能为0）
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//它不在controller中，为了返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody//它不在controller中，为了返回数据
    public R error(GuliException e){
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
