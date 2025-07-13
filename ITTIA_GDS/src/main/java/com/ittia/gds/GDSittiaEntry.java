package com.ittia.gds;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import com.ittia.gds.ui.GDSEMRFrame;

public class GDSittiaEntry extends Application {

    private static final String[] BUTTON_NAMES = {
        "Log In", "Prologue", "Version Information", "Rescue", "Ittia Start", "Quit"
    };

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ITTIA Launcher");

        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        createButtons(root);

        Scene scene = new Scene(root, 300, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createButtons(VBox root) {
        for (String name : BUTTON_NAMES) {
            Button button = new Button(name);
            button.setPrefWidth(200);
            button.setOnAction(e -> {
                try {
                    handleButtonPress(name);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            root.getChildren().add(button);
        }
    }

    private void handleButtonPress(String buttonText) throws IOException {
        switch (buttonText) {
            case "Prologue":
                System.out.println("Prologue selected.");
                break;
            case "Version Information":
                System.out.println("Version 1.0 - July 2025.");
                break;
            case "Ittia Start":
                System.out.println("Launching ITTIA...");
                GDSEMRFrame.main(null);
                break;
            case "Rescue":
                System.out.println("Rescue action triggered.");
                break;
            case "Quit":
                System.out.println("Exiting application.");
                System.exit(0);
                break;
            default:
                System.err.println("Unrecognized action for button: " + buttonText);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}