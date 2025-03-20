package com.example.cookies.controller;

import com.example.cookies.repository.entities.Cookie;
import com.example.cookies.service.CookieService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CookieController {

    CookieService cookieService;

    public CookieController(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @GetMapping("/cookies")
    public ResponseEntity<List<Cookie>> getAllCookies() {
        return ResponseEntity.ok(cookieService.findAllCookies());
    }

    @GetMapping("/cookies/available")
    public ResponseEntity<List<Cookie>> getAllAvailableCookies() {
        return ResponseEntity.ok(cookieService.findAllCookiesAvailable());
    }

    @GetMapping("/cookies-ai")
    public ResponseEntity<List<Cookie>> getAllCookiesByQuery(@Param(value = "query") String query) {
        return ResponseEntity.ok(cookieService.findAllCookiesByQuery(query));
    }
}
