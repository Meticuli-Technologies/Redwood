package com.meti;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpContent();

        List<Lesson> lessons = new ArrayList<>();
        Path path = Paths.get(".\\lessons");
        try {
            List<Path> mainPaths = Files.
                    walk(path).
                    filter(path1 -> path1.toString().contains("main")).
                    collect(Collectors.toList());
            for (Path mainPath : mainPaths) {
                lessons.add(loadMain(mainPath));
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        for (Lesson lesson : lessons) {
            TitledPane titledPane = new TitledPane();
            titledPane.setTranslateX(width / 2);
            titledPane.setTranslateY(height / 2);
            titledPane.setPrefSize(100, 100);
            content.getChildren().add(titledPane);
        }
    }

    private void setUpContent() {
        width = 1000;
        height = 1000;
        content.setMinSize(width, height);
        scrollPane.setHvalue(scrollPane.getHmin());
        scrollPane.setVvalue(scrollPane.getVmin());
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

        NodeList preList = document.getElementsByTagName("preList");
        for (int i = 0; i < preList.getLength(); i++) {
            Node node = preList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String content = element.getTextContent();
                List<String> contentList = new ArrayList<>();
                Arrays.stream(content.split(" "))
                        .filter(s -> !s.trim().equals(""))
                        .collect(Collectors.toList())
                        .forEach(s -> contentList.add(s.trim().replace("\n", "")));
                pres.addAll(contentList);
            }
        }

        NodeList subList = document.getElementsByTagName("subList");
        for (int i = 0; i < subList.getLength(); i++) {
            Node node = subList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String content = element.getTextContent();
                List<String> contentList = new ArrayList<>();
                Arrays.stream(content.split(" "))
                        .filter(s -> !s.trim().equals(""))
                        .collect(Collectors.toList())
                        .forEach(s -> contentList.add(s.trim().replace("\n", "")));
                subs.addAll(contentList);
            }
        }

        NodeList pageList = document.getElementsByTagName("page");
        for (int i = 0; i < pageList.getLength(); i++) {
            Node node = pageList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String content = element.getTextContent();
                List<String> contentList = new ArrayList<>();
                Arrays.stream(content.split(" "))
                        .filter(s -> !s.trim().equals(""))
                        .collect(Collectors.toList())
                        .forEach(s -> contentList.add(s.trim().replace("\n", "")));
                pages.addAll(contentList);
            }
        }

        reader.close();

        return new Lesson(pres, subs, pages);
    }
}