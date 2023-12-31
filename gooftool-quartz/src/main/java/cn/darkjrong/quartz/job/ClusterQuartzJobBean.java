package cn.darkjrong.quartz.job;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * CustomQuartzJobBean代替org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean，解决序列化的问题
 *
 * @author Rong.Jia
 * @date 2021/10/06
 */
@Setter
@Slf4j
@PersistJobDataAfterExecution
public class ClusterQuartzJobBean extends QuartzJobBean implements ApplicationContextAware {

    private String targetObject;

    private String targetMethod;

    private Object[] params;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        try {
            log.debug("The scheduled task starts：targetObject={}, targetMethod={}", targetObject, targetMethod);
            Object targetObject = applicationContext.getBean(this.targetObject);
            Method m = targetObject.getClass().getMethod(targetMethod, toClass(params));
            m.invoke(targetObject, params);
            log.debug("The scheduled task ends normally：targetObject={}, targetMethod={}", this.targetObject, targetMethod);
        } catch (final Exception e) {
            log.error("The scheduled task execution failed：targetObject=" + targetObject + ", targetMethod=" + targetMethod, e);
        }
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static Class<?>[] toClass(final Object... array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Class[0];
        }
        final Class<?>[] classes = new Class[array.length];
        for (int i = 0; i < array.length; i++) {
            classes[i] = array[i] == null ? null : array[i].getClass();
        }
        return classes;
    }

}
