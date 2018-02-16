package com.meti.lesson;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/16/2018
 */
public class LessonFactory {
    public static Lesson loadMain(Path mainPath) throws IOException, ParserConfigurationException, SAXException {
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

        return new Lesson(mainPath, name, description, pres, subs, pages);
    }

    public static String loadDescription(Document document) {
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

    public static void loadList(List<String> toStore, Document document, String name) {
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
}
