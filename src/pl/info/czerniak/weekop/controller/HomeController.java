package pl.info.czerniak.weekop.controller;

import pl.info.czerniak.weekop.comparator.DiscoveryVoteUpComparator;
import pl.info.czerniak.weekop.model.Discovery;
import pl.info.czerniak.weekop.service.DiscoveryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet (urlPatterns = "")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        saveDiscoveriesInRequest(req);
        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req,resp);
    }

    private void saveDiscoveriesInRequest(HttpServletRequest req) {
        DiscoveryService discoveryService = new DiscoveryService();
        List<Discovery> discoveryList = discoveryService.getAllDiscoveries(new DiscoveryVoteUpComparator());
        req.setAttribute("discoveries", discoveryList);
    }
}
