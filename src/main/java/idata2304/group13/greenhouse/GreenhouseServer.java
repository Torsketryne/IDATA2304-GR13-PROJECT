package idata2304.group13.greenhouse;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * A server for the greenhouse system.
 * <p>
 * Using ExecutorService to make sure the class can be shut down properly.
 * While also allowing for multiple threads.
 * <p>
 * I have used the following sources to help me with this class:
 * <a href="https://coderanch.com/t/709877/java/TCP-server-connections-executor-service">Excecutor usage for TCP with multi thread functions</a>
 * <a href="https://stackoverflow.com/questions/12588476/multithreading-socket-communication-client-server">Multhithreading communication for client server communication</a>
 *
 * @author MoldyDaniel
 */
public class GreenhouseServer {
    private static final int PORT = 1313;
    private ServerSocket serverSocket;
    private final ExecutorService executor;

    /**
     * Create a new server.
     */
    public GreenhouseServer() {
        executor = Executors.newCachedThreadPool();
    }


    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                executor.submit(() -> handleConnection(socket));
            }
        } catch (IOException e) {
            System.err.println("Error while running server: " + e.getMessage());
        } finally {
            stopServer();
        }
    }

    /**
     * Handle a connection to a client.
     *
     * @param socket The socket to communicate with the client.
     */
    private void handleConnection(Socket socket) {
        try (PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                System.out.println("Received message: " + inputLine);
                output.println("Connected to server:" + inputLine);
            }
        } catch (IOException e) {
            System.err.println("Error while communicating with client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error while closing socket: " + e.getMessage());
            }
        }
    }


        public void stopServer() {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error while shutting down server: " + e.getMessage());
            }
            executor.shutdown();
        }
    }



