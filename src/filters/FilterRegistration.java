package filters;

import data.UserDB;
import data.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebFilter(filterName = "RegistrationFilter", urlPatterns = "/registration")
public class FilterRegistration implements Filter {
    private UserDB userDB = new UserDB();
    ArrayList<User> list = userDB.select();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        User user_in_db = new User(name,login,password);
        System.out.println(list);
        if(login != null & password!= null & name!=null) {
            for (User user : list) {
                if (user_in_db.getLogin().equals(user.getLogin())) {
                    req.setAttribute("message", "This login is used now");
                }
            }
            userDB.insert(user_in_db);
            req.getSession().setAttribute("user", user_in_db);
            req.getRequestDispatcher("/welcome.jsp").forward(req,resp);
        }
    }
}
