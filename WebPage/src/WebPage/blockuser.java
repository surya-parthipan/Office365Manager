package WebPage;

import appLayer.userdetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "blockuser")
public class blockuser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userdetail user = new userdetail();
        request.setAttribute("id",request.getParameter("id"));
        if(user.blockuser(request.getParameter("id"))){
            request.setAttribute("errorMessage","user is Blocked!! :) ");
            request.getRequestDispatcher("/blockuser.jsp").forward(request, response);
        }
        else{
            request.setAttribute("errorMessage","Failed to Block the User! Retry!!!");
            request.getRequestDispatcher("/blockuser.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
