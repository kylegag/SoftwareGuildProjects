/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movie.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyle
 */
public class Movie {

    static int nextId = 1;
    String title;
    String releaseDate;
    String rating;
    String director;
    String studio;
    String notes;


    int id;

    public Movie() {
        this.id = generateNextId();
    }

    public Movie(int id) {
        if (0 <= id) {
            this.id = id;
        } else {
            this.id = generateNextId();
        }
    }

    public static int generateNextId() {
        return nextId++;
    }

    public static void resetNextId(int id) {
        nextId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }
}
