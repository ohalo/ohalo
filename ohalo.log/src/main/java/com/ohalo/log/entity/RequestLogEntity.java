/*
 * 
 */
package com.ohalo.log.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <pre>
 * 功能：RequestLogEntity 请求日志对象.
 * 
 *  model层的定位：
 *  1.MVC系统中的Model部分从概念上可以分为两类：
 *    系统的内部状态和改变系统状态的动作。
 *  2.Struts为Model部分提供了Action和ActionForm对象：
 *    所有的Action处理器对象都是开发者从Struts的Action类派生的子类。
 *    Action处理器对象封装了具体的处理逻辑，
 *    调用业务逻辑模块，并且把响应提交到合适的View组件以产生响应。
 *  3.Struts提供的ActionForm组件对象，它可以通过定义属性描述客户端表单数据。
 *    开发者可以从它派生子类对象，
 *    利用它和Struts提供的自定义标记库结合可以实现对客户端的表单数据的良好封装和支持，
 *    Action处理器对象可以直接对它进行读写，
 *    而不再需要和request、response对象进行数据交互。
 *  4.通过ActionForm组件对象实现了对View和Model之间交互的支持。
 *    Struts通常建议使用一组JavaBean表示系统的内部状态，
 *    根据系统的复杂度也可以使用像Entity EJB 和 Session EJB等组件来实现系统状态。
 *  5.Struts建议在实现时把"做什么"（Action）和"如何做"（业务逻辑）分离。
 *    这样可以实现业务逻辑的重用。
 * 作者：赵辉亮
 * 日期：2013-5-10上午10:26:41
 * </pre>
 */
public class RequestLogEntity implements Serializable {

  //
  private static final long serialVersionUID = 1L;

  // requestID
  private String requestID;
  // 客户端ip
  private String clientIP;
  // 客户端浏览器
  private String clientUserAgent;
  // 服务端ip
  private String serverIP;
  // 服务端端口
  private Integer serverPort;
  // URI
  private String requestURI;
  // 请求用户
  private String user;
  // 请求应用
  private String appName;
  // 请求模块
  private String moduleName;
  // 请求action
  private String action;
  // 请求参数
  private String reqParameters;
  // 请求开始时间
  private Date reqStartTime;
  // 请求结束时间
  private Date reqEndTime;
  // 请求时长
  private Long reqDuration;

  /***
   * @return the requestID
   */
  public String getRequestID() {
    return requestID;
  }

  /***
   * @param requestID
   *          the requestID to set
   */
  public void setRequestID(String requestID) {
    this.requestID = requestID;
  }

  /***
   * @return the clientIP
   */
  public String getClientIP() {
    return clientIP;
  }

  /***
   * @param clientIP
   *          the clientIP to set
   */
  public void setClientIP(String clientIP) {
    this.clientIP = clientIP;
  }

  /***
   * @return the clientUserAgent
   */
  public String getClientUserAgent() {
    return clientUserAgent;
  }

  /***
   * @param clientUserAgent
   *          the clientUserAgent to set
   */
  public void setClientUserAgent(String clientUserAgent) {
    this.clientUserAgent = clientUserAgent;
  }

  /***
   * @return the serverIP
   */
  public String getServerIP() {
    return serverIP;
  }

  /***
   * @param serverIP
   *          the serverIP to set
   */
  public void setServerIP(String serverIP) {
    this.serverIP = serverIP;
  }

  /***
   * @return the serverPort
   */
  public Integer getServerPort() {
    return serverPort;
  }

  /***
   * @param serverPort
   *          the serverPort to set
   */
  public void setServerPort(Integer serverPort) {
    this.serverPort = serverPort;
  }

  /***
   * @return the requestURI
   */
  public String getRequestURI() {
    return requestURI;
  }

  /***
   * @param requestURI
   *          the requestURI to set
   */
  public void setRequestURI(String requestURI) {
    this.requestURI = requestURI;
  }

  /***
   * @return the user
   */
  public String getUser() {
    return user;
  }

  /***
   * @param user
   *          the user to set
   */
  public void setUser(String user) {
    this.user = user;
  }

  /***
   * @return the appName
   */
  public String getAppName() {
    return appName;
  }

  /***
   * @param appName
   *          the appName to set
   */
  public void setAppName(String appName) {
    this.appName = appName;
  }

  /***
   * @return the moduleName
   */
  public String getModuleName() {
    return moduleName;
  }

  /***
   * @param moduleName
   *          the moduleName to set
   */
  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  /***
   * @return the action
   */
  public String getAction() {
    return action;
  }

  /***
   * @param action
   *          the action to set
   */
  public void setAction(String action) {
    this.action = action;
  }

  /***
   * @return the reqParameters
   */
  public String getReqParameters() {
    return reqParameters;
  }

  /***
   * @param reqParameters
   *          the reqParameters to set
   */
  public void setReqParameters(String reqParameters) {
    this.reqParameters = reqParameters;
  }

  /***
   * @return the reqStartTime
   */
  public Date getReqStartTime() {
    return reqStartTime;
  }

  /***
   * @param reqStartTime
   *          the reqStartTime to set
   */
  public void setReqStartTime(Date reqStartTime) {
    this.reqStartTime = reqStartTime;
  }

  /***
   * @return the reqEndTime
   */
  public Date getReqEndTime() {
    return reqEndTime;
  }

  /***
   * @param reqEndTime
   *          the reqEndTime to set
   */
  public void setReqEndTime(Date reqEndTime) {
    this.reqEndTime = reqEndTime;
  }

  /***
   * @return the reqDuration
   */
  public Long getReqDuration() {
    return reqDuration;
  }

  /***
   * @param reqDuration
   *          the reqDuration to set
   */
  public void setReqDuration(Long reqDuration) {
    this.reqDuration = reqDuration;
  }

  /**
   * 
   * <pre>
   * 方法体说明：实现toString方法
   * 作者：赵辉亮
   * 日期：2013-7-26
   * </pre>
   * 
   * @return
   */
  public String toString() {
    return jsonToString();
  }

  /**
   * 
   * <pre>
   * 方法体说明：输出sql局域形式的toString方法
   * 作者：赵辉亮
   * 日期：2013-7-26
   * </pre>
   * 
   * @return
   */
  public String insertSqlToString() {
    return "insert into T_LOG_REQLOG(FREQUESTID,FCLIENTIP,FCLIENTUSERAGENT,FSERVERIP,"
        + "FSERVERPORT,FREQUESTURI,FUSER,FAPPNAME,FMODULENAME,FACTION,FREQPARAMETERS,"
        + "FREQSTARTTIME,FREQENDTIME,FREQDURATION)values('"
        + requestID
        + "','"
        + clientIP
        + "','"
        + clientUserAgent
        + "','"
        + serverIP
        + "','"
        + serverPort
        + "','"
        + requestURI
        + "','"
        + user
        + "','"
        + appName
        + "','"
        + moduleName
        + "','"
        + action
        + "','"
        + reqParameters
        + "','"
        + reqStartTime
        + "','"
        + reqEndTime
        + "','"
        + reqDuration + "')";
  }

  /**
   * 
   * <pre>
   * 方法体说明：转换成json性质的输出
   * 作者：赵辉亮
   * 日期：2013-7-26
   * </pre>
   * 
   * @return
   */
  public String jsonToString() {
    return "{requestID:" + requestID + ", clientIP:" + clientIP
        + ", clientUserAgent:" + clientUserAgent + ", serverIP:" + serverIP
        + ", serverPort:" + serverPort + ", requestURI:" + requestURI
        + ", user:" + user + ", appName:" + appName + ", moduleName:"
        + moduleName + ", action:" + action + ", reqParameters:"
        + reqParameters + ", reqStartTime:" + reqStartTime + ", reqEndTime:"
        + reqEndTime + ", reqDuration:" + reqDuration + "}";
  }

}
