
<%@ page import="appLayer.User" %>
<%@ page import="appLayer.userdetail" %>
<%
    if ("1" != session.getAttribute("loggedIn")) {
        request.setAttribute("errorMessage","Unauthorized Access");
        out.println(request.getAttribute("errorMessage"));
        request.getRequestDispatcher("/login.jsp").forward(request, response);
        return;
    }
    if(session.getAttribute("role").equals("user")){
        request.setAttribute("errorMessage","Unauthorized Access");
        out.println(request.getAttribute("errorMessage"));
        request.getRequestDispatcher("/manage.jsp").forward(request, response);
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

    <title>Office Management</title>

    <!-- Bootstrap Core CSS -->
    <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

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
                <h1 class="page-header">Form</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Create User
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" action="/createuser" method="post">
                                    <div class="form-group">
                                        <label>Enter Display Name</label>
                                        <input class="form-control" name="name" placeholder="Display Name">
                                        <label>Enter Mail NickName</label>
                                        <input class="form-control" name="nickname" placeholder="NickName">
                                        <label>Enter Password</label>
                                        <input class="form-control" name="pass" placeholder="Password">
                                        <label>Enter Location</label>
                                        <select class="form-control" name="loc" >
                                            <option value="IN">India</option>
                                            <option value="US">United States</option>
                                            <option value="AU">Australia</option>
                                        </select>
                                    </div>
                                    <button type="submit" class="btn btn-success">Create User</button>
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

</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="../vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="../vendor/metisMenu/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="../dist/js/sb-admin-2.js"></script>

</body>

</html>
