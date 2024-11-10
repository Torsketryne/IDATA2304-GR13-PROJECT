import java.io.*;
import java.net.Socket;
import java.util.Random;

public class SensorNode {
    private String nodeId;
    private double temperature;
    private double humidity;
    private double light;
    private double nitrogen;
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private Random random = new Random();

    public SensorNode(String id, String serverHost, int serverPort){
        this.nodeId = id;
        try {
            this.socket = new Socket(serverHost, serverPort);
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    private String sensorDataString() {
        return String.format("Node ID: %s, Temperature: %.2f, Humidity: %.2f, Light: %.2f, Nitrogen: %.2f",
                this.nodeId, this.temperature, this.humidity, this.light, this.nitrogen);
    }


    // method to send the sensor data to the server.
    private String sendSensorData() throws IOException {
        this.temperature = 20 + random.nextDouble() * 10;
        this.humidity = 40 + random.nextDouble() * 20;
        this.light = 100 + random.nextDouble() * 50;
        this.nitrogen = 0 + random.nextDouble() * 100;
        writer.write(sensorDataString() + "\n");
        writer.flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reader.readLine();
    }
}


