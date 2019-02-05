<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 10/6/2018
  Time: 11:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="appLayer.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if ("1" != session.getAttribute("loggedIn")) {
        request.setAttribute("errorMessage","Unauthorized Access");
        out.println(request.getAttribute("errorMessage"));
        request.getRequestDispatcher("/login.jsp").forward(request, response);
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Manage Office365</title>

    <!-- Bootstrap Core CSS -->
    <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/manage.jsp">Office365 Manager</a>
        </div>
        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <%--<li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>--%>
                    <%--</li>--%>
                    <li><a href="/settings.jsp"><i class="fa fa-gear fa-fw"></i> Settings</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <form action="/logout" method="post">
                            <button class="bg-primary badge center-block"><i class="fa fa-sign-out fa-fw"></i> Logout</button>
                        </form>
                    </li>
                </ul>
            </li>
        </ul>


        <!-- /.navbar-header -->



        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="sidebar-search">
                        <div class="input-group custom-search-form">
                            <input type="text" class="form-control" placeholder="Search...">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                        <!-- /input-group -->
                    </li>
                    <li>
                        <a href="/manage.jsp"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Users<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="listuser.jsp">List Users</a>
                            </li>
                            <%
                                if (session.getAttribute("role").equals("manager")){
                                    out.print("<li><a href=\"/createuser.jsp\">Create User</a></li>");
                                    out.print("<li><a href=\"#\"><i class=\"fa fa-bar-chart-o fa-fw\"></i> Users Management<span class=\"fa arrow\"></span></a><ul class=\"nav nav-third-level\"><li><a href=\"resetuserpass.jsp\">Reset Password</a></li><li><a href=\"deluser.jsp\">Delete User</a></li><li><a href=\"blockuser.jsp\">Block/UnBlock Users</a></li>\n");
                                }
                            %>
                            <%
                                if (session.getAttribute("role").equals("admin")){
                                    out.print("<li><a href=\"/createuser.jsp\">Create User</a>");
                                    out.print("<li><a href=\"/userActivities.jsp\">Assign License</a></li>");
                                    out.print("<li><a href=\"#\"><i class=\"fa fa-bar-chart-o fa-fw\"></i> Users Management<span class=\"fa arrow\"></span></a><ul class=\"nav nav-third-level\"><li><a href=\"resetuserpass.jsp\">Reset Password</a></li><li><a href=\"deluser.jsp\">Delete User</a></li><li><a href=\"blockuser.jsp\">Block/UnBlock Users</a></li>\n");
                                }
                            %>
                        </ul>
                    </li>
                </ul>
                </li>
                <li>
                    <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Groups<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="listgroups.jsp">List Groups</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="/settings.jsp"><i class="fa fa-cog fa-fw"></i>Settings</a>
                </li>
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Reset Password</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Reset the user passsword......
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" action="/deluser" method="post">
                                    <div class="form-group">
                                        <label>Select User</label>
                                        <select class="form-control" name="id">
                                            <%

                                                User userObject = new User();
                                                StringBuilder s = userObject.sendGET("http://localhost:49806/api/Users/");
                                                String res = s.toString();
                                                res = res.substring(1,res.length()-1);
                                                String[] output = res.split(",");
                                                for (int i=0;i<output.length;i+=4){
                                                    String op = output[i+1].replaceAll("\"","");
                                                    String pr = output[i].replaceAll("\"","");
                                                    out.print("<option value="+pr+">" + op + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div>
                                        <%
                                            if(null!=request.getAttribute("errorMessage"))
                                            {
                                                out.println(request.getAttribute("errorMessage"));
                                            }
                                        %>
                                    </div>
                                    <button type="submit" class="btn btn-danger">Delete User</button>
                                    <button type="reset" class="btn btn-primary">Reset</button>
                                </form>
                            </div>
                        </div>
                        <!-- /.row (nested) -->
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /#page-wrapper -->

    <!-- jQuery -->
    <script src="../vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="../vendor/raphael/raphael.min.js"></script>
    <script src="../vendor/morrisjs/morris.min.js"></script>
    <script src="../data/morris-data.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
</div>
</body>

</html>

