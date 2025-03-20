package com.example.cookies.service.impl;

import com.example.cookies.repository.CookieRepository;
import com.example.cookies.repository.entities.Cookie;
import com.example.cookies.service.CookieService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CookieServiceImpl implements CookieService {

    private OllamaChatModel chatModel;
    private CookieRepository cookieRepository;

    public CookieServiceImpl(CookieRepository cookieRepository, OllamaChatModel chatModel) {
        this.cookieRepository = cookieRepository;
        this.chatModel = chatModel;
    }

    @Override
    public List<Cookie> findAllCookies() {
        return cookieRepository.findAll();
    }

    @Override
    public List<Cookie> findAllCookiesAvailable() {
        return cookieRepository.findAllByAvailableTrue();
    }

    @Override
    public List<Cookie> findAllCookiesByQuery(String query) {
        String prompt = """
                You are a database assistant. Convert the following natural language query into a database action.
                Query: "%s"
                Your response should only be one of the following:
                - GET_AVAILABLE_COOKIES if the user asks for available cookies.
                - GET_ALL_COOKIES if the user asks for all cookies.
                - GET_COOKIES_BY_PRACE if the user asks for cookies by price.
                - UNKNOWN if the query is unclear.
                
                If the query contains a price condition, return it in the exact format:
                - GET_COOKIES_BY_PRICE: > {price} for queries like "greater than 5".
                - GET_COOKIES_BY_PRICE: < {price} for queries like "less than 5".
                - GET_COOKIES_BY_PRICE: = {price} for queries like "equal to 5".
                """.formatted(query);

        final Prompt ollama_prompt = new Prompt(
                prompt,
                OllamaOptions.builder()
                        .model(OllamaModel.LLAMA3)
                        .temperature(0.4)
                        .build()
        );
        ChatResponse call = this.chatModel.call(ollama_prompt);
        final String response = call.getResult().getOutput().getText();

        if (response.equals("GET_AVAILABLE_COOKIES")) {
            return cookieRepository.findAllByAvailableTrue();
        } else if (response.equals("GET_ALL_COOKIES")) {
            return cookieRepository.findAll();
        } else if (response.startsWith("GET_COOKIES_BY_PRICE")) {
            String[] parts = response.split(":");
            if (parts.length == 2) {
                String condition = parts[1].trim();
                double parsedPrice = Double.parseDouble(condition.substring(1).trim());
                if (condition.startsWith(">")) {
                    return cookieRepository.findAllByPriceGreaterThan(parsedPrice);
                } else if (condition.startsWith("<")) {
                    return cookieRepository.findAllByPriceLessThan(parsedPrice);
                } else if (condition.startsWith("=")) {
                    return cookieRepository.findAllByPriceEquals(parsedPrice);
                }
            }
        }
        return List.of();
    }
}
