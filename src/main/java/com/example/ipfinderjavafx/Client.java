package com.example.ipfinderjavafx;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application{
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5,5,5,5));
        paneForTextField.setStyle("-fx-border-color: green");
        //paneForTextField.setLeft(new Label("Skriv til serveren"));

        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);


        BorderPane mainPane = new BorderPane();
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(paneForTextField);
        ta.setStyle("-fx-display-caret: false;");
        Scene scene = new Scene(mainPane, 450,450);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();

        ta.appendText("Indtast en hjemme side for at se dens IP adresse." + '\n');
        ta.appendText("f.eks www.google.dk" + '\n');
        tf.setOnAction(e -> {
            try{
                String hjemmeside = tf.getText();
                toServer.writeUTF(hjemmeside);
                toServer.flush();

                String svarFraServer = fromServer.readUTF();

                ta.appendText("Besked fra serveren " + '\n' + svarFraServer + '\n');
            }
            catch (IOException ex) {
                System.err.println(ex);
            }
        });

        try{
            Socket socket = new Socket("localhost", 8000);

            fromServer = new DataInputStream(socket.getInputStream());

            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex) {
            ta.appendText(ex.toString() + '\n');
        }
    }
}