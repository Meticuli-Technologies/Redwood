package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/15/2018
 */
public class LessonPane {
    @FXML
    private TitledPane namePane;

    @FXML
    private TextArea descriptionArea;

    public void loadLesson(Lesson lesson){
        namePane.setText(lesson.getName());
        descriptionArea.setText(lesson.getDescription());
    }
}
