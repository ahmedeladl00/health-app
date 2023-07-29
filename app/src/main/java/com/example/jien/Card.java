package com.example.jien;

public class Card {

    private final boolean answered;
    private final int image;
    private final String title;

    public Card(boolean answered, int image, String title) {
        this.answered = answered;
        this.image = image;
        this.title = title;
    }

    public boolean isAnswered() {
        return answered;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

}
