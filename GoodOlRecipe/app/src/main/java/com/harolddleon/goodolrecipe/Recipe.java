package com.harolddleon.goodolrecipe;

public class Recipe {
    private String title;
    private String info;
    private String description;
    private String link;
    private int imageResource;

    Recipe(String title, String info, String description, String link, int imageResource) {
        this.title = title;
        this.info = info;
        this.description = description;
        this.link = link;
        this.imageResource = imageResource;
    }

    String getLink() {
        return link;
    }

    void setLink(String link) {
        this.link = link;
    }

    String getInfo() {
        return info;
    }

    void setInfo(String info) {
        this.info = info;
    }

    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    int getImageResource() {
        return imageResource;
    }

    void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
