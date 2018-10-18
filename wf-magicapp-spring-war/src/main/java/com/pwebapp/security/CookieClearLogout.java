package com.pwebapp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieClearLogout {
    public void cookieClearLogout(HttpServletRequest request, HttpServletResponse reponse);
}
