package com.pwebapp.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class CookieClearLogoutImpl implements CookieClearLogout {

    @Override
    public void cookieClearLogout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath(request.getContextPath() + "/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
