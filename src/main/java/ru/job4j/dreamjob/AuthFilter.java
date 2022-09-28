package ru.job4j.dreamjob;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthFilter implements Filter {
    private static final List<String> END_POINTS = List.of(
            "loginPage", "login",
            "formRegisterUser", "registration",
            "fail", "success");
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();


        /*
        Если запрос идет к адресам loginPage или login, то мы их пропускаем сразу.
         */
        if (checkEndPoint(uri)) {
            filterChain.doFilter(req, res);
            return;
        }
        /*
        Если запросы идут к другим адресам, то проверяем наличие пользователя в HttpSession
        Если его нет, то мы переходим на страницу авторизации.
         */
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        filterChain.doFilter(req, res);
    }

    private boolean checkEndPoint(String requestUri) {
        return END_POINTS.stream().anyMatch(requestUri::endsWith);
    }
}
