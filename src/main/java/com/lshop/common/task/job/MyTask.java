package com.lshop.common.task.job;

import java.lang.reflect.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.gv.core.proxy.ServiceConstants;

public class MyTask {
	private String targetObject;
	private String targetMethod;
	private static final Log logger	= LogFactory.getLog(MyTask.class);
	public void run() {
		if (ServiceConstants.beanFactory != null) {
			logger.info(targetObject+"==="+targetMethod);
			// 反射调用方法
			Object bean = ServiceConstants.beanFactory.getBean(targetObject);
			if (bean != null) {
				Class clazz = bean.getClass();
				try {
					Method m = clazz.getDeclaredMethod(targetMethod);
					m.invoke(bean, null);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}

	public String getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public String getTargetMethod() {
		return targetMethod;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

}