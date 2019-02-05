package WebPage;

import appLayer.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "userActivity")
public class userActivity extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userObject = new User();

        request.setAttribute("user_id", request.getParameter("user_id"));
        request.setAttribute("licPack", request.getParameter("licPack"));

        if (userObject.lic(request.getParameter("user_id"), request.getParameter("licPack"))) {
            request.setAttribute("errorMessage","License Added to the User Successfully! :)");
            request.getRequestDispatcher("/userActivities.jsp").forward(request, response);
        } else
        {
            request.setAttribute("errorMessage","Failed to Assign License to the user! :(");
            request.getRequestDispatcher("/userActivities.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/errorpage.jsp").forward(request,response);
    }
}
