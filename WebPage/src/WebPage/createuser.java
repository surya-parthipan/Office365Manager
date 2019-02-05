package WebPage;

import appLayer.userdetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "createuser.jsp")
public class createuser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userdetail user = new userdetail();
        request.setAttribute("name",request.getParameter("name"));
        request.setAttribute("nickname",request.getParameter("nickname"));
        request.setAttribute("pass",request.getParameter("pass"));
        request.setAttribute("loc",request.getParameter("loc"));

        if(user.createUSer(request.getParameter("name"),request.getParameter("nickname"),request.getParameter("pass"),request.getParameter("loc"))){

            request.getRequestDispatcher("/listuser.jsp").forward(request,response);
        }
        else{
            request.getRequestDispatcher("/manage.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/errorpage.jsp").forward(request,response);
    }
}
