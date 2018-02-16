package com.meti.lesson;

import java.nio.file.Path;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/16/2018
 */
public class LessonView {

    public void loadLesson(Lesson lesson) {
        List<Path> paths = lesson.convertPagesToPaths();
    }
}
