package org.ohalo.base.interceptor;

import javax.servlet.http.HttpServletResponse;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class CORSInterceptor implements MethodInterceptor {
  private String allowOrigin = "*";

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Object[] args = invocation.getArguments();
    for (Object object : args) {
      if (object instanceof HttpServletResponse) {
        ((HttpServletResponse) object).addHeader("Access-Control-Allow-Origin",
            allowOrigin);
        ((HttpServletResponse) object).addHeader(
            "Access-Control-Allow-Headers",
            "origin, x-requested-with, content-type");
        break;
      }
    }
    return invocation.proceed();
  }
}
