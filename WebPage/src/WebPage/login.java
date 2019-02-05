package WebPage;
import appLayer.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "login.jsp")
public class login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        User userObject = new User();

        request.setAttribute("username", request.getParameter("loginname"));
        request.setAttribute("password", request.getParameter("password"));

        if (userObject.isValidUserCredentials(request.getParameter("loginname"), request.getParameter("password"))) {
            request.getSession().setAttribute("role", userObject.getRole(request.getParameter("loginname"), request.getParameter("password")));
            request.getSession().setAttribute("loggedIn", "1");
            request.getSession().setAttribute("email", request.getParameter("loginname"));
            request.getRequestDispatcher("/manage.jsp").forward(request, response);
        } else
        {
            request.setAttribute("errorMessage","Invalid login and password. Try again");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/errorpage.jsp").forward(request,response);
    }
}