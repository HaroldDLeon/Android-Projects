package com.harolddleon.goodolrecipe;

public class Recipe {
    private String title;
    private String info;
    private int imageResource;

    Recipe(String title, String info, int imageResource) {
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    void setInfo(String info) {
        this.info = info;
    }

    void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    String getInfo() {
        return info;
    }

    String getTitle() {
        return title;
    }

    int getImageResource() {
        return imageResource;
    }
}
