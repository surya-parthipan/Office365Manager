<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 10/4/2018
  Time: 11:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <title>Office Management</title>
  </head>
  <body>
      <h1>hello world</h1>
      <p>Body text. This is my first webapp JSP page.</p>
      <%
          Date date = new Date();
          out.print("<h2>" + date.toString() + "</h2>");
      %>
  </body>
</html>
