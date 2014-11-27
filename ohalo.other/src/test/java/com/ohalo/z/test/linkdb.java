package com.ohalo.z.test;

import java.sql.*;

public class linkdb {
      private Connection  con = null;
      private final String url = "jdbc:microsoft:sqlserver://";
      private String serverName;
      private String portNumber;
      private String databaseName;
      private String userName;
      private String password;
      // Informs the driver to use server a side-cursor,
      // which permits more than one active statement
      // on a connection.
     private final String selectMethod = "cursor";
      // Constructor
      public linkdb(){
       serverName= Server.serverName;
       portNumber = Server.portNumber;
       databaseName= Server.databaseName;
       userName = Server.userName;
       password = Server.password;
      }
      public linkdb(String as_ServerName,String as_portnumber,String as_databaseName,String as_userName,String as_password){
       serverName= as_ServerName;
       portNumber = as_portnumber;
       databaseName= as_databaseName;
       userName = as_userName;
       password = as_password;
      }
      private String getConnectionUrl(){
       /**
        * 获取数据库连接信息
        */
           return url+serverName+":"+portNumber+";databaseName="+databaseName+";selectMethod="+selectMethod+";";
      }
      public Connection getConnection(){
       /**
        * 连接到数据库,成功后返回Connection对象引用
        */
           try{
                Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                con = java.sql.DriverManager.getConnection(getConnectionUrl(),userName,password);
             //   if(con!=null) System.out.println("Connection Successful!");
           }catch(Exception e){
                e.printStackTrace();
                System.out.println("Error Trace in getConnection() : " + e.getMessage());
          }
           return con;
       }
      public void displayDbProperties(){
       /**
        * 列出数据库的部分信息
        */
           java.sql.DatabaseMetaData dm = null;
           java.sql.ResultSet rs = null;
           try{
                con= this.getConnection();
                if(con!=null){
                     dm = con.getMetaData();
                     System.out.println("Driver Information");
                     System.out.println("\tDriver Name: "+ dm.getDriverName());
                     System.out.println("\tDriver Version: "+ dm.getDriverVersion ());
                     System.out.println("\nDatabase Information ");
                     System.out.println("\tDatabase Name: "+ dm.getDatabaseProductName());
                     System.out.println("\tDatabase Version: "+ dm.getDatabaseProductVersion());
                     System.out.println("Avalilable Catalogs ");
                     rs = dm.getCatalogs();
                     while(rs.next()){
                          System.out.println("\tcatalog: "+ rs.getString(1));
                     }
                     rs.close();
                     rs = null;
                     closeConnection();
                }else System.out.println("Error: No active Connection");
           }catch(Exception e){
                e.printStackTrace();
           }
           dm=null;
      }    
      private void closeConnection(){
       /**
        * 关闭数据库连接
        */
           try{
                if(con!=null)
                     con.close();
                con=null;
           }catch(Exception e){
                e.printStackTrace();
           }
      }

      public static void main(String[] args) throws Exception
        {
       linkdb myDbTest = new linkdb();
           myDbTest.displayDbProperties();
        }
}
