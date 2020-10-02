package me.spike.consumer.web.contract;

public class Greet {
    private String message;

    public Greet(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
