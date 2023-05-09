package com.example.jien;

public class Card {

    private boolean answered;
    private int image;
    private String title;

    public Card(boolean answered, int image, String title) {
        this.answered = answered;
        this.image = image;
        this.title = title;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
