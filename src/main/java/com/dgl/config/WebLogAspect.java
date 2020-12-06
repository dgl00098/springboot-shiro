package com.dgl.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dgl.common.Constants;

import com.dgl.smodel.entity.User;
import com.dgl.smodel.vo.RespEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Enumeration;

/**
 * aop实现web层的日志切面
 * @author DGL
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

	ThreadLocal<Long> startTime = new ThreadLocal<Long>();

	    /**
	     * 定义一个切入点
		 * 1:返回值类型 包名 模块名  类名 方法名 参数类型
	     */
	     @Pointcut("execution( * com.dgl.controller..*.*(..))")
	     public void webLog(){

		 }

	     /**
	      * 处理日志，打印请求相关数据
	      * @param joinPoint
	      */
	     @Before("webLog()")
	     public void doBefore(JoinPoint joinPoint){
	        startTime.set(System.currentTimeMillis());

	       // 接收到请求，记录请求内容
	        log.info("WebLogAspect.doBefore()");
	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			 if(attributes==null){
				 return;
			 }
			 HttpServletRequest request = attributes.getRequest();

	      // 记录下请求内容
	        log.info("URL : " + request.getRequestURL().toString());
	        log.info("HTTP_METHOD : " + request.getMethod());
	        log.info("IP : " + request.getRemoteAddr());
	        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

	        if(joinPoint.getArgs()!=null){
	       	int index=joinPoint.getArgs().length;
			   if(index!=0){
				   index=index-1;
				   Object obj=joinPoint.getArgs()[index];
				   if(! (obj instanceof HttpServletRequest)){
					   //忽略掉错误的getter属性
					   log.info("ARGS : " + JSON.toJSONString(obj, SerializerFeature.IgnoreErrorGetter));
				   }
			   }
		   }else{
			   Enumeration<String> enu=request.getParameterNames();
			   while(enu.hasMoreElements()){
				   String paraName=enu.nextElement();
				   System.out.println(paraName+": "+request.getParameter(paraName));
			   }
		   }
	     }

	     /**
	      * 处理完请求，返回内容
	      */
	     @AfterReturning(returning="result", pointcut = "webLog()")
	     public void doAfterReturning(Object result){
	        log.info("WebLogAspect.doAfterReturning()");
	        String res = JSON.toJSONString(result);
	        log.info("返回数据长度："+res.length());
			 int i = 2000;
			 if(res.length()<= i){
	        	log.info("返回数据："+res);
	        }
	        log.info("耗时（毫秒） : " + (System.currentTimeMillis() - startTime.get()));
			 startTime.remove();
	     }

}
