package pl.info.czerniak.weekop.controller;

import pl.info.czerniak.weekop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("inputUsername");
        String password = req.getParameter("inputPassword");
        String email = req.getParameter("inputEmail");
        UserService userService = new UserService();
        userService.addUser(username, email, password);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
