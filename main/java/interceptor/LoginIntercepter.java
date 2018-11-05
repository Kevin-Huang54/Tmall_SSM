package interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

//访问需要登陆的页面之前，判断是否登陆
public class LoginIntercepter extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        //获取请求名称
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
        //无需登陆也能访问的白名单
        String[] noNeedAuthPage = new String[]{
                "Home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"};

        if (uri.startsWith("/fore")) {
            //取出/fore后面的部分，也就是对应method
            String method = StringUtils.substringAfterLast(uri, "/fore");
            //判断是否在白名单中
            if (!Arrays.asList(noNeedAuthPage).contains(method)) {
                User user = (User) session.getAttribute("user");
                if (null == user) {
                    //如果没有登陆，跳转到登陆页面
                    response.sendRedirect("loginPage");
                    return false;
                }
            }
        }
        return true;
    }

    //controller完成后，访问页面前的处理，不做处理
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    //在访问页面后的处理，不做处理
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {

    }
}
