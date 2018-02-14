package com.meti;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TreeView implements Initializable {
    @FXML
    private AnchorPane treeViewPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path path = Paths.get(".\\lessons");
        try {
            List<Path> mainPaths = Files.
                    walk(path).
                    filter(path1 -> path1.toString().contains("main")).
                    collect(Collectors.toList());
            for (Path mainPath : mainPaths) {
                loadMain(mainPath);
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void loadMain(Path mainPath) throws IOException, ParserConfigurationException, SAXException {
        BufferedReader reader = Files.newBufferedReader(mainPath);
        String name = reader.readLine();
        List<String> files = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            files.add(line);
        }

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document document = builder.parse(Files.newInputStream(mainPath));
        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName("page");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                String content = element.getTextContent();

                System.out.println(content);
            }
        }

        reader.close();
    }
}
