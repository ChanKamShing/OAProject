package com.cjs.interceptor;

import com.cjs.bean.User;
import com.cjs.util.OaContants;
import com.cjs.util.UserHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        /** 拦截用户的请求       */
        String requestUrl = request.getRequestURI().toString();
        System.out.println("requestUrl:" + requestUrl);
        User user = (User) request.getSession().getAttribute(OaContants.USER_SESSION);
        if (user != null) {
            UserHolder.addCurrentUser(user);
            return true;
        } else {
            response.sendRedirect(request.getContextPath()+"/OA/login");
            System.out.println("getContextPath:" + request.getContextPath());
            System.out.println("requestUrl:" + requestUrl+"——》被拦截");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception e) throws Exception {
        UserHolder.removeCurrentUser();
    }
}
