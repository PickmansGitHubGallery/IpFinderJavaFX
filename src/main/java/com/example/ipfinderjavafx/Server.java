package com.example.ipfinderjavafx;
import java.io.*;
import java.time.LocalTime;
import java.util.Date;
import java.net.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application{


    @Override
    public void start(Stage stage) throws Exception {
        TextArea ta = new TextArea();
        Scene scene = new Scene(new ScrollPane(ta),450,450);
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();

        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(() ->
                        ta.appendText("Serveren startede " + new Date() + '\n'));

                Socket socket = serverSocket.accept();
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());

                String url = inputFromClient.readUTF();
                InetAddress inetAddress = InetAddress.getByName(url);
                String ip = inetAddress.getHostAddress();

                outputToClient.writeUTF(ip);
                outputToClient.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
