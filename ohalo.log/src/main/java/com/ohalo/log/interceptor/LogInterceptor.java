package com.ohalo.log.interceptor;

import java.util.logging.Level;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.ohalo.log.interceptor.convert.LogConvert;

public class LogInterceptor implements MethodInterceptor {

	private LogConvert logConvert;

	private void before(MethodInvocation invocation) {
		try {
			logConvert
					.before(invocation.getMethod(), invocation.getArguments());
		} catch (Exception e) {
			logConvert.logInfo(Level.WARNING, "[LogConvert.before] 执行出现异常", e);
		}
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		logConvert.logInfo(Level.CONFIG, "[LogConvert] 执行开始", null);
		before(invocation);
		Object result = null;
		try {
			result = invocation.proceed();
		} catch (Throwable e) {
			logConvert.logInfo(Level.WARNING, "[LogInterceptor] 执行出现异常!", e);
		}
		after(result);
		logConvert.logInfo(Level.CONFIG, "[LogConvert] 执行結束", null);
		return null;
	}
	private void after(Object result) {

		try {
			logConvert.after(result);
		} catch (Exception e) {
			logConvert.logInfo(Level.WARNING, "[LogConvert.after] 执行出现异常", e);
		}

	}

	public LogConvert getLogConvert() {
		return logConvert;
	}

	public void setLogConvert(LogConvert logConvert) {
		this.logConvert = logConvert;
	}
	
	

}
