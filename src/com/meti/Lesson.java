package com.meti;

import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/15/2018
 */
public class Lesson {
    private final String name;
    private final List<String> pres;
    private final List<String> subs;
    private final List<String> pages;

    public Lesson(String name, List<String> pres, List<String> subs, List<String> pages) {
        this.name = name;
        this.pres = pres;
        this.subs = subs;
        this.pages = pages;
    }

    public String getName() {
        return name;
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
