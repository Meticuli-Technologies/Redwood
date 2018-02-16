package com.meti.lesson;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/16/2018
 */
public class LessonView {
    private List<Path> pagePaths;
    private int currentPage;

    @FXML
    private AnchorPane contentPane;

    @FXML
    public void back() {
        try {
            loadPage(currentPage - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPage(int pageIndex) throws IOException {
        currentPage = pageIndex;
        Path path = pagePaths.get(pageIndex);

        String[] nameArgs = path.getName(path.getNameCount() - 1).toString().split(".");
        String extension = nameArgs[nameArgs.length - 1];

        ObservableList<Node> children = contentPane.getChildren();
        children.clear();
        switch (extension) {
            case "txt":
                TextArea area = new TextArea();
                area.setEditable(false);

                BufferedReader reader = Files.newBufferedReader(path);
                List<String> lines = new ArrayList<>();

                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }

                for (int i = 0; i < lines.size(); i++) {
                    area.appendText(lines.get(i));

                    if (i != lines.size() - 1) {
                        area.appendText("\n");
                    }
                }

                children.add(area);
                break;
            case "fxml":
                children.add(FXMLLoader.load(path.toUri().toURL()));
                break;
            default:
                throw new IOException("Don't know how to read file " + path.toString());
        }
    }

    @FXML
    public void next() {
        try {
            loadPage(currentPage + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLesson(Lesson lesson) throws IOException {
        pagePaths = lesson.convertPagesToPaths();
        if (pagePaths.size() == 0) {
            throw new IllegalArgumentException("Not enough paths specified, no content found!");
        }

        loadPage(0);
    }
}
