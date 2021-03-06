package com.meti.lesson;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/15/2018
 */
public class Lesson {
    private final Path location;

    private final String name;
    private final String description;
    private final List<String> pres;
    private final List<String> subs;
    private final List<String> pages;

    public Lesson(Path location, String name, String description, List<String> pres, List<String> subs, List<String> pages) {
        this.location = location;
        this.name = name;
        this.description = description;
        this.pres = pres;
        this.subs = subs;
        this.pages = pages;
    }

    public Path getLocation() {
        return location;
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

    public List<Path> convertPagesToPaths() {
        List<Path> pathList = new ArrayList<>();
        pages.forEach(s -> pathList.add(Paths.get(".\\" + s)));
        return pathList;
    }
}
