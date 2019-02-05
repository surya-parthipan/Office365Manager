package WebPage;

import appLayer.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "getdata")
public class getdata extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userObject = new User();

        request.setAttribute("clientID", request.getParameter("clientID"));
        request.setAttribute("tenantAdd", request.getParameter("tenantAdd"));
        request.setAttribute("appKey", request.getParameter("appKey"));
        request.setAttribute("email", request.getParameter("email"));
        if(userObject.getData(request.getParameter("clientID"),request.getParameter("tenantAdd"), request.getParameter("appKey"),request.getParameter("email"))){
            request.getSession().setAttribute("gotDetails", "1");
            request.getSession().setAttribute("email", request.getParameter("email"));
            request.getRequestDispatcher("/manage.jsp").forward(request, response);
        }
        else {
            request.setAttribute("errorMessage","Invalid Data. Try again");
            request.getSession().setAttribute("gotDetails", "0");
            request.getRequestDispatcher("/settings.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/errorpage.jsp").forward(request,response);
    }
}
