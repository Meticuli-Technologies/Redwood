package com.meti;

import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/15/2018
 */
public class Lesson {
    private final String name;
    private final String description;
    private final List<String> pres;
    private final List<String> subs;
    private final List<String> pages;

    public Lesson(String name, String description, List<String> pres, List<String> subs, List<String> pages) {
        this.name = name;
        this.description = description;
        this.pres = pres;
        this.subs = subs;
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getPres() {
        return pres;
    }

    public List<String> getSubs() {
        return subs;
    }

    public List<String> getPages() {
        return pages;
    }
}
