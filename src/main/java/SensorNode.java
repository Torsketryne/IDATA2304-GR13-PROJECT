import java.io.*;
import java.net.Socket;
import java.util.Random;

public class SensorNode {
    private String id;
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private Random random = new Random();

    public SensorNode(String id, String serverHost, int serverPort){
        this.id = id;
        try {
            this.socket = new Socket(serverHost, serverPort);
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

}

