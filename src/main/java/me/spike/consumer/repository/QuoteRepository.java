package me.spike.consumer.repository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteRepository {

    private RestTemplate restTemplate;

    public QuoteRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRandomQuote(String id) {
        final QuoteResponse quoteResponse = restTemplate
                .getForObject(String.format("/quote?id=%s", id), QuoteResponse.class);
        return quoteResponse.getMessage();
    }
}
