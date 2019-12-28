package com.example.tab_widget;

public class Item {

    private int image;
    private String imageTitle;

    public Item(int image, String imageTitle) {
        this.image = image;
        this.imageTitle = imageTitle;
    }

    public int getImage() {
        return image;
    }

    public String getImageTitle() {
        return imageTitle;
    }
}