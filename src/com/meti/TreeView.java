package com.meti;

import com.meti.lesson.Lesson;
import com.meti.lesson.LessonFactory;
import com.meti.lesson.LessonPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TreeView implements Initializable {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane content;

    private int width;
    private int height;
    private URL lessonPaneFXML;

    {
        try {
            lessonPaneFXML = Paths.get(".\\resources\\fxml\\LessonPane.fxml").toUri().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Lesson> lessons = loadLessons();
            loadComponents(lessons);
            loadContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadComponents(List<Lesson> lessons) throws IOException {
        width = lessons.size() * 400;
        height = 1000; //TODO: height change

        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            FXMLLoader loader = new FXMLLoader(lessonPaneFXML);
            Parent parent = loader.load();
            parent.setTranslateX(i * 300 + 100);
            parent.setTranslateY(500);
            ((LessonPane) loader.getController()).loadLesson(lesson);
            content.getChildren().add(parent);
        }
    }

    private List<Lesson> loadLessons() throws Exception {
        List<Lesson> lessons = new ArrayList<>();
        Path path = Paths.get(".\\lessons");
        List<Path> mainPaths = Files.
                walk(path).
                filter(path1 -> path1.toString().contains("main")).
                collect(Collectors.toList());

        for (Path mainPath : mainPaths) {
            lessons.add(LessonFactory.loadMain(mainPath));
        }

        return lessons;
    }

    private void loadContent() {
        content.setMinSize(width, height);
        scrollPane.setHvalue(scrollPane.getHmin());
        scrollPane.setVvalue(scrollPane.getVmin());
        scrollPane.setPannable(true);
    }
}