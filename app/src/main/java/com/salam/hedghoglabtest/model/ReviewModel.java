package com.salam.hedghoglabtest.model;

public class ReviewModel {
    private String author;
    private String content;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReviewModel(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public ReviewModel() {
    }
}