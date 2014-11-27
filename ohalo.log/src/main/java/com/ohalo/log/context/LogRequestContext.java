package com.ohalo.log.context;

import java.util.UUID;

/**
 * 
 * <pre>
 * 功能：LogRequestContext
 *  工具类的定位：
 *  1.作为一个软件开发者，缺乏想象力是最严重的罪过之一。
 *    我们经常把事情重复做一遍又一遍，但是我们很少改变这种方式。
 *    经过这些年开发，在我的工具箱里面有了一些每个项目我都需要用到的工具，
 *    烦人的重复工作不再是我的事.
 *  2.工具，汉语词语，原指工作时所需用的器具，
 *    后引申为为达到、完成或促进某一事物的手段。
 *    它的好处可以是机械性，也可以是智能性的。
 *    大部分工具都是简单机械。
 *    例如一根铁棍可以当作槓杆使用，力点离开支点越远，杠杆传递的力就越大.
 *    而工具类就是通过实现独立功能单元的一个工具
 *  3.直接使用的工具类，不但可以在本应用中使用这些工具类，也可以在其它的应用中使用，
 *    这些工具类中的大部分是可以在脱离各种框架和应用时使用的。
 *    工具类并在程序编写时适当使用和提取，将有助于提高开发效率、增强代码质量。
 *  4.对于软件开发过程中，需要使用到各种框架，而框架中往往都提供了相应的工具类。
 *    比如spring、struts、ibatis、hibernate。
 *    而java本身也提供不少的工具类供开发员进行特殊的处理
 * 作者：赵辉亮
 * 日期：2013-5-10上午10:28:38
 * </pre>
 */
public final class LogRequestContext {

  /**
   * 远程请求方法
   */
  private String remoteReqMethod;
  /**
   * 远程请求url
   */
  private String remoteReqURL;

  /**
   * 远程请求地址
   */
  private String remoteAddr;

  /**
   * 
   * 远程请求模块铭桐城
   */
  private String moduleName;

  /**
   * 请求id
   */
  private String requestId;

  /**
   * 请求用户id
   */
  public String userId;

  /**
   * 
   * <pre>
   * 方法体说明：get()方法
   * 作者：赵辉亮
   * 日期：2013-7-24
   * </pre>
   * 
   * @return
   */
  public String getRequestId() {
    return requestId;
  }

  /**
   * 
   * <pre>
   * 方法体说明：get() method;
   * 作者：赵辉亮
   * 日期：2013-7-24
   * </pre>
   * 
   * @return
   */
  public String getModuleName() {
    return moduleName;
  }

  /**
   * 
   * <pre>
   * 方法体说明：get method()
   * 作者：赵辉亮
   * 日期：2013-7-24
   * </pre>
   * 
   * @return
   */
  public String getRemoteRequestMethod() {
    return remoteReqMethod;
  }

  /**
   * 
   * <pre>
   * 方法体说明：get method()
   * 作者：赵辉亮
   * 日期：2013-7-24
   * </pre>
   * 
   * @return
   */
  public String getRemoteRequestURL() {
    return remoteReqURL;
  }

  /**
   * 
   * <pre>
   * 方法体说明：clear it ,the  context info 
   * 作者：赵辉亮
   * 日期：2013-7-24
   * </pre>
   */
  public void clear() {
    remoteReqMethod = null;
    remoteReqURL = null;
    moduleName = null;
    remoteAddr = null;
  }

  /**
   * 
   * <pre>
   * 方法体说明：get method()
   * 作者：赵辉亮
   * 日期：2013-7-24
   * </pre>
   * 
   * @return
   */
  public String getUserId() {
    return userId;
  }

  /**
   * 
   * <pre>
   * 方法体说明：get current context();
   * 作者：赵辉亮
   * 日期：2013-7-24
   * </pre>
   * 
   * @return
   */
  public static LogRequestContext getCurrentContext() {
    return (LogRequestContext) context.get();
  }

  /**
   * 
   * <pre>
   * 方法体说明：set current context info 
   * 作者：赵辉亮
   * 日期：2013-7-24
   * </pre>
   * 
   * @param remoteReqMethod
   *          remote request (Req) methodName
   * @param remoteReqURL
   *          remote request url
   * @param remoteAddr
   *          remote request Address
   * @param userId
   *          user code
   */
  public static void setCurrentContext(String remoteReqMethod,
      String remoteReqURL, String remoteAddr, String userId) {
    LogRequestContext context = getCurrentContext();
    context.remoteReqMethod = remoteReqMethod;
    context.remoteReqURL = remoteReqURL;
    context.remoteAddr = remoteAddr;
    context.userId = userId;
  }

  /**
   * 
   * <pre>
   * 方法体说明：set moduel name to current Context
   * 作者：赵辉亮
   * 日期：2013-7-24
   * </pre>
   * 
   * @param moduleName
   */
  public static void setCurrentContext(String moduleName) {
    LogRequestContext context = getCurrentContext();
    context.moduleName = moduleName;
  }

  /**
   * 
   * <pre>
   * 方法体说明：remove context info ,
   *  can see {@link LogRequestContext#clear()}
   * 作者：赵辉亮
   * 日期：2013-7-24
   * </pre>
   */
  public static void remove() {
    LogRequestContext context = getCurrentContext();
    context.clear();
  }

  /**
   * 
   * <pre>
   * 方法体说明：get remote Address
   * 作者：赵辉亮
   * 日期：2013-7-24
   * </pre>
   * 
   * @return
   */
  public String getRemoteAddr() {
    return remoteAddr;
  }

  /**
   * context thread loacl
   */
  private static ThreadLocal<LogRequestContext> context = new ThreadLocal<LogRequestContext>() {
    protected LogRequestContext initialValue() {
      String uid = UUID.randomUUID().toString();
      LogRequestContext rc = new LogRequestContext();
      rc.requestId = uid;
      return rc;
    }
  };
}
