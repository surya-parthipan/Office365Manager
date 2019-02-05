package WebPage;

import appLayer.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "sign_up")
public class sign_up extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userObject = new User();

        request.setAttribute("username", request.getParameter("loginname"));
        request.setAttribute("password", request.getParameter("password"));
        request.setAttribute("password", request.getParameter("rpassword"));

        if(userObject.checkusr(request.getParameter("loginname"))){
            if(request.getParameter("password").equals(request.getParameter("rpassword"))){
                if(userObject.sign_up(request.getParameter("loginname"), request.getParameter("password"))){
                    request.setAttribute("errorMessage","Account Created Successfully");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
                else {
                    request.setAttribute("errorMessage","Invalid! Please, Try again");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            }
            else {
                request.setAttribute("errorMessage","Password doesn't match");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
        else {
            request.setAttribute("errorMessage","Username Already exists");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/errorpage.jsp").forward(request,response);
    }
}
