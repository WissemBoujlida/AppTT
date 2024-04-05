package com.example.apptt;

public class Offer {

    private String name;
    private String type;
    private String briefDescription;
    private String description;
    private int image;

    public Offer(String name, String type, String briefDescription, String description, int image) {
        this.name = name;
        this.type = type;
        this.briefDescription = briefDescription;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
