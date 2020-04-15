package filters;

import data.User;
import data.UserDB;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter(filterName = "EnterFilter", urlPatterns = "/enter")
public class FilterEnter implements Filter {
    private UserDB userDB = new UserDB();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();

        if(nonNull(session) && nonNull(session.getAttribute("user"))){
            req.getRequestDispatcher("/welcome.jsp").forward(req,resp);
        } else{
            User user = UserDB.selectOne(login,password);
            if(user != null){
                req.getSession().setAttribute("user", user);
                req.getRequestDispatcher("/welcome.jsp").forward(req,resp);
            } else{
                if(login != null){
                    req.setAttribute("message", "Wrong log or pass");
                }
                req.getRequestDispatcher("/enter.jsp").forward(req, resp);
            }
        }

    }
}
