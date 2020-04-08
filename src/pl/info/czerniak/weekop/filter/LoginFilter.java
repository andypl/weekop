package pl.info.czerniak.weekop.filter;

import pl.info.czerniak.weekop.model.User;
import pl.info.czerniak.weekop.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(request.getUserPrincipal() != null && request.getSession().getAttribute("user") == null){
            saveUserInSession(request);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private void saveUserInSession(HttpServletRequest request) {
        UserService userService = new UserService();
        String username = request.getUserPrincipal().getName();
        User user = userService.getUserByUsername(username);
        request.getSession(true).setAttribute("user",user);
        System.out.println(user.getId());
    }

    @Override
    public void destroy() {

    }
}
