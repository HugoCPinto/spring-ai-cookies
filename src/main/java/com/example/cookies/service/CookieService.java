package com.example.cookies.service;

import com.example.cookies.repository.entities.Cookie;

import java.util.List;

public interface CookieService {

    // never expose entities. This is just an example
    List<Cookie> findAllCookies();

    // never expose entities. This is just an example
    List<Cookie> findAllCookiesAvailable();

    // never expose entities. This is just an example
    List<Cookie> findAllCookiesByQuery(String query);
}
