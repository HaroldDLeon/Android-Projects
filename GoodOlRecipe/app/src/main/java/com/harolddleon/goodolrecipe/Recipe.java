package com.harolddleon.goodolrecipe;

public class Recipe {
    private String title;
    private String info;
    private String description;
    private int imageResource;

    Recipe(String title, String info, String description, int imageResource) {
        this.title = title;
        this.info = info;
        this.description = description;
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

    void setDescription(String description) {
        this.description = description;
    }

    String getInfo() {
        return info;
    }

    String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }

    int getImageResource() {
        return imageResource;
    }
}
