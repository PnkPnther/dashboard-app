package com.example.demo;
import java.time.LocalDate;

public class LocalEvent {
    private String description;
    private LocalDate date;

    //GETTERS AND SETTERS FOR DATE AND DESCRIPTION FIELDS
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    //CONSTRUCTOR METHOD TO SET EVENT TO DISPLAY IN LISTVIEW/BOX
    public LocalEvent(LocalDate date, String description) {
        this.setDate(date);
        this.setDescription(description);
    }

    //MAKES EVENT DISPLAY IN GUI PROPERLY
    @Override
    public String toString() {
        return "On " + this.getDate() + ": " + this.getDescription();
    }
}
