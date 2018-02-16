package com.meti.lesson;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

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
    private URL lessonView;
    private Lesson lesson;

    {
        try {
            lessonView = Paths.get(".\\resources\\fxml\\LessonView.fxml").toUri().toURL();
        } catch (MalformedURLException e) {
            //TODO: handle try-catches
            e.printStackTrace();
        }
    }

    public void loadLesson(Lesson lesson) {
        this.lesson = lesson;

        namePane.setText(lesson.getName());
        descriptionArea.setText(lesson.getDescription());
    }

    @FXML
    public void start() {
        try {
            //TODO: setting for opening lesson in previous window
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(lessonView);
            Scene scene = new Scene(loader.load());

            LessonView view = loader.getController();
            view.loadLesson(lesson);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
