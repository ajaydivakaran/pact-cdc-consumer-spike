package me.spike.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import me.spike.consumer.repository.QuoteResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "quoteService", port = "9090")
class ConsumerPactTest {

    @Pact(provider="quoteService", consumer="greetingService")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("with quote id1")
                .uponReceiving("random quote request")
                .path("/quote")
                .method("GET")
                .matchQuery("id", "1")
                .willRespondWith()
                .status(200)
                .body("{\"message\": \"random message\", \"id\" : \"id1\"}")
                .headers(Collections.singletonMap("Content-Type", "application/json"))
                .toPact();
    }

    @Test
    public void shouldReturnSuccessfulQuoteResponse(MockServer mockServer) {
        ResponseEntity<QuoteResponse> response = new RestTemplate()
                .getForEntity(mockServer.getUrl() + "/quote?id=1", QuoteResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get("Content-Type").contains("application/json")).isTrue();
        assertThat(response.getBody().getMessage()).isEqualTo("random message");
        assertThat(response.getBody().getId()).isEqualTo("id1");
    }


}
