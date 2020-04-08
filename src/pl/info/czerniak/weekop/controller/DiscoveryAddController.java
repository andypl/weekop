package pl.info.czerniak.weekop.controller;

import pl.info.czerniak.weekop.model.User;
import pl.info.czerniak.weekop.service.DiscoveryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (urlPatterns = "/add")
public class DiscoveryAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getUserPrincipal() != null){
            req.getRequestDispatcher("WEB-INF/new.jsp").forward(req, resp);
        }else{
            resp.sendError(403);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("inputName");
        String description = req.getParameter("inputDescription");
        String url = req.getParameter("inputUrl");
        User authenticatedUser = (User) req.getSession().getAttribute("user");
        if(req.getUserPrincipal() != null){
            DiscoveryService discoveryService = new DiscoveryService();
            discoveryService.addDiscovery(name,description,url,authenticatedUser);
            resp.sendRedirect(req.getContextPath() + "/");
        }else{
            resp.sendError(403);
        }
    }
}
