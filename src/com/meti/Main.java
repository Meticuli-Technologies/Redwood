package com.meti;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Main extends Application {
    private URL treeViewFXML;


    public Main() {
        try {
            treeViewFXML = new File("resources\\fxml\\TreeView.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(treeViewFXML);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
