package com.meti;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
            lessons.add(loadMain(mainPath));
        }

        return lessons;
    }

    private Lesson loadMain(Path mainPath) throws IOException, ParserConfigurationException, SAXException {
        BufferedReader reader = Files.newBufferedReader(mainPath);

        List<String> pres = new ArrayList<>();
        List<String> subs = new ArrayList<>();
        List<String> pages = new ArrayList<>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document document = builder.parse(Files.newInputStream(mainPath));
        document.getDocumentElement().normalize();

        String name = ((Element) document.getElementsByTagName("name").item(0)).getAttribute("value");
        String description = loadDescription(document);

        loadList(pres, document, "preList");
        loadList(subs, document, "subList");
        loadList(pages, document, "page");

        reader.close();

        return new Lesson(name, description, pres, subs, pages);
    }

    private void loadList(List<String> toStore, Document document, String name) {
        NodeList nodeList = document.getElementsByTagName(name);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String content = element.getTextContent();
                List<String> contentList = new ArrayList<>();
                Arrays.stream(content.split(" "))
                        .filter(s -> !s.trim().equals(""))
                        .collect(Collectors.toList())
                        .forEach(s -> contentList.add(s.trim().replace("\n", "")));
                toStore.addAll(contentList);
            }
        }
    }

    private void loadContent() {
        content.setMinSize(width, height);
        scrollPane.setHvalue(scrollPane.getHmin());
        scrollPane.setVvalue(scrollPane.getVmin());
    }

    private String loadDescription(Document document) {
        List<String> lines = new ArrayList<>();

        if (document.getElementsByTagName("desc") != null) {
            loadList(lines, document, "desc");
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                builder.append(line);

                if (i != lines.size() - 1) {
                    builder.append("\n");
                }
            }
            return builder.toString();
        } else {
            return "No description was found for this lesson.";
        }
    }
}