package WebPage;

import appLayer.userdetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "resetpass")
public class resetpass extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userdetail user = new userdetail();
        request.setAttribute("passwrd",request.getParameter("passwrd"));
        request.setAttribute("id",request.getParameter("id"));
        if(user.resetpass(request.getParameter("passwrd"),request.getParameter("id"))){
            request.setAttribute("errorMessage","Password is changed Successfully! :) ");
            request.getRequestDispatcher("/resetuserpass.jsp").forward(request, response);
        }
        else{
            request.setAttribute("errorMessage","Failed to change the Password the user! :( <br> Passwords must contain at least 8 characters, including special characters,letters and numbers.");
            request.getRequestDispatcher("/resetuserpass.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
