package WebPage;
import appLayer.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "welcome.jsp")
public class welcome extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User userObject = new User();

        request.setAttribute("clientID", request.getParameter("clientID"));
        request.setAttribute("tenantAdd", request.getParameter("tenantAdd"));
        request.setAttribute("appKey", request.getParameter("appKey"));

        userObject.getDetails(request.getParameter("clientID"),request.getParameter("tenantAdd"),request.getParameter("appKey"));
        request.getRequestDispatcher("/manage.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/errorpage.jsp").forward(request,response);
    }
}
