package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class AutoFillAspect {
    @Pointcut("execution(void com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillpt() {}

    /**
     * 公共字段赋值
     * @param proceedingJoinPoint
     */
    @Around("autoFillpt()")
    public void autoFill(ProceedingJoinPoint proceedingJoinPoint){
        /**
         * 获取方法注解的value值
         */
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType value = annotation.value();
        /**
         * 获取方法参数
         */
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];

        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        /**
         * 字段填充
         */
        if (value == OperationType.INSERT){
            try {
                Method setcreateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setupdateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setcreateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setupdateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setcreateTime.invoke(arg,now);
                setupdateTime.invoke(arg,now);
                setcreateUser.invoke(arg,currentId);
                setupdateTime.invoke(arg,now);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (value ==OperationType.UPDATE){
            try {
                Method setupdateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setupdateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setupdateTime.invoke(arg,now);
                setupdateTime.invoke(arg,now);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        try {
            proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }
}
