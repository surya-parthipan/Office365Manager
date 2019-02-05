<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 10/6/2018
  Time: 11:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
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
                <h1 class="page-header">Dashboard</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-user fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"></div>
                                <div>List Users</div>
                            </div>
                        </div>
                    </div>
                    <a href="/listuser.jsp">
                        <div class="panel-footer">
                            <span class="pull-left">View Details</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-green">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-search  fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"></div>
                                <div>Search User</div>
                            </div>
                        </div>
                    </div>
                    <a href="/searchuser.jsp">
                        <div class="panel-footer">
                            <span class="pull-left">View Details</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-red">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-user-plus  fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"></div>
                                <div>Create User</div>
                            </div>
                        </div>
                    </div>
                    <a href="createuser.jsp">
                        <div class="panel-footer">
                            <span class="pull-left">View Details</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-yellow">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-users fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"></div>
                                <div>List Groups</div>
                            </div>
                        </div>
                    </div>
                    <a href="listgroups.jsp">
                        <div class="panel-footer">
                            <span class="pull-left">View Details</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>

            <!-- /.row -->
            <div class="row">
                <div class="col-lg-6">
                    <div class="panel panel-default">
                        <form action="/refuser" method="post">
                            <div class="panel-heading">
                                <i class="fa fa-bar-chart-o fa-fw"></i>User Chart
                                <div class="pull-right">
                                    <div class="btn-group" >
                                        <button type="submit" class="btn btn-default btn-xs" >
                                            <i class="fa fa-refresh" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <%
                            java.sql.Connection connec = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "abc123");
                            Statement statement = connec.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM counts_table where id = 1");
                            ArrayList<Integer> numbers = new ArrayList<Integer>();
                            while (resultSet.next()) {
                                numbers.add(resultSet.getInt("inactive_users"));
                                numbers.add(resultSet.getInt("licensed_users"));
                                numbers.add(resultSet.getInt("total_users"));
                                numbers.add(resultSet.getInt("unlicensed_users"));
                            }
                            int usr_cnt=numbers.get(2);
                            int lic_usr=numbers.get(1);
                            int unlic_usr=numbers.get(3);
                            int inact_usr=numbers.get(0);
                        %>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div id="morris-donut-chart"></div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-6 -->
                <div class="col-lg-6">
                    <div class="panel panel-default">
                        <form action="/refgroup" method="post">
                            <div class="panel-heading">
                                <i class="fa fa-bar-chart-o fa-fw"></i>Group Chart
                                <div class="pull-right">
                                    <div class="btn-group" >
                                        <button type="submit" class="btn btn-default btn-xs" >
                                            <i class="fa fa-refresh" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <%
                            java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "abc123");
                            Statement st = conn.createStatement();
                            ResultSet rs = statement.executeQuery("SELECT * FROM group_table where id = 1");
                            ArrayList<Integer> num = new ArrayList<Integer>();
                            while (rs.next()) {
                                num.add(rs.getInt("total_grp"));
                                num.add(rs.getInt("sec_grp"));
                                num.add(rs.getInt("dis_grp"));
                                num.add(rs.getInt("off_grp"));
                            }
                            int tot_cnt=num.get(0);
                            int sec_cnt=num.get(1);
                            int dis_grp=num.get(2);
                            int off_grp=num.get(3);
                        %>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div id="morris1-donut-chart"></div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-6 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../vendor/metisMenu/metisMenu.min.js"></script>
    <!-- Morris Charts JavaScript -->
    <script src="../vendor/raphael/raphael.min.js"></script>
    <script src="../vendor/morrisjs/morris.min.js"></script>
    <%--<script src="../data/morris-data.js"></script>--%>
    <script>
        $(function() {


            Morris.Donut({
                element: 'morris-donut-chart',
                data: [{
                    label: "Total Users",
                    value: "<%= usr_cnt %>"
                }, {
                    label: "No.Of Inactive Users",
                    value: "<%= inact_usr %>"
                }, {
                    label: "No.Of Licensed Users",
                    value: "<%= lic_usr %>"
                }, {
                    label: "No.Of UnLicensed Users",
                    value: "<%= unlic_usr %>"
                }],
                resize: true
            });
            Morris.Donut({
                element: 'morris1-donut-chart',
                data: [{
                    label: "Total Group",
                    value: "<%= tot_cnt %>"
                }, {
                    label: "No.Of Security Group",
                    value: "<%= sec_cnt %>"
                }, {
                    label: "No.Of Distribution Group",
                    value: "<%= dis_grp %>"
                }, {
                    label: "No.Of Office365 Group",
                    value: "<%= off_grp %>"
                }],
                resize: true
            });

        });
    </script>
    <!-- Flot Charts JavaScript -->
    <script src="../vendor/flot/excanvas.js"></script>
    <script src="../vendor/flot/jquery.flot.js"></script>
    <script src="../vendor/flot/jquery.flot.pie.js"></script>
    <script src="../vendor/flot/jquery.flot.resize.js"></script>
    <script src="../vendor/flot/jquery.flot.time.js"></script>
    <script src="../vendor/flot-tooltip/jquery.flot.tooltip.min.js"></script>
    <script src="../data/flot-data.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
</div>
</body>

</html>

