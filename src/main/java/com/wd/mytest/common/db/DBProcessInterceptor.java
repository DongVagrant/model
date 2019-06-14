package com.wd.mytest.common.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 用于切换数据源的DB AOP 
 * @author GuoYJ @create 2016年10月31日
 */
@Order(1)
@Aspect
@Component
public class DBProcessInterceptor {
	Log logger = LogFactory.getLog(getClass());
	/**
     * @throws Throwable 
     * @方法说明:定义描目标包名,以描包名下指定的注解USEDB进行切换
     */
    @Around("within(com.wd.mytest.dao..*) && @annotation(db)")
    public Object doBasicProfiling(ProceedingJoinPoint pjp, UseDB db) throws Exception {
        DynamicDB.setDB(db.value());
        Object object = null;
        try {
            object = pjp.proceed();
        } catch (Throwable e) {
            logger.error("数据库切换抛错：" + e.getMessage(), e);
            throw (Exception) e;
        }
        // 继续执行该方法
        DynamicDB.setDefault();
        return object;
    }
}
