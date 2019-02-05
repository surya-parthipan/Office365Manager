package WebPage;

import appLayer.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "refuser")
public class refuser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userobj = new User();
        if(userobj.usercount()){
            request.setAttribute("errorMessage","User Details are refreshed Successfully! :) ");
            request.getRequestDispatcher("/manage.jsp").forward(request,response);
        }
        else{
            request.setAttribute("errorMessage","Failed to refresh the data :( ");
            request.getRequestDispatcher("/manage.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
