package dataBase.config;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;


//@Component
//@ConditionalOnProperty(name = "sql-logging", havingValue = "true", matchIfMissing = false)
public class DatasourceProxyBeanPostProcessor //implements BeanPostProcessor
{

//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
//    {
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
//    {
//        if (bean instanceof DataSource)
//        {
//            ProxyFactory factory = new ProxyFactory(bean);
//            factory.setProxyTargetClass(true);
//            factory.addAdvice(new ProxyDataSourceInterceptor((DataSource) bean));
//            return factory.getProxy();
//        }
//        return bean;
//    }
//
//    private static class ProxyDataSourceInterceptor implements MethodInterceptor
//    {
//        private final DataSource dataSource;
//
//        public ProxyDataSourceInterceptor(final DataSource dataSource)
//        {
//            this.dataSource = ProxyDataSourceBuilder.create(dataSource).name("Batch-Insert-Logger").asJson().countQuery().logQueryToSysOut().build();
//        }
//
//        @Override
//        public Object invoke(final MethodInvocation invocation) throws Throwable
//        {
//            Method proxyMethod = ReflectionUtils.findMethod(dataSource.getClass(), invocation.getMethod().getName());
//            if (proxyMethod != null)
//            {
//                return proxyMethod.invoke(dataSource, invocation.getArguments());
//            }
//            return invocation.proceed();
//        }
//    }
}
