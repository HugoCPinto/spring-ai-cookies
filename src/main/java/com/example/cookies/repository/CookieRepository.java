package com.example.cookies.repository;

import com.example.cookies.repository.entities.Cookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookieRepository extends JpaRepository<Cookie, Long> {
    List<Cookie> findAllByAvailableTrue();
    List<Cookie> findAllByPriceGreaterThan(double price);
    List<Cookie> findAllByPriceLessThan(double price);
    List<Cookie> findAllByPriceEquals(double price);
}
